package dev.kush.security2.service.impl;

import dev.kush.security2.models.ConformationToken;
import dev.kush.security2.models.Customer;
import dev.kush.security2.models.CustomerDto;
import dev.kush.security2.models.EmailDetails;
import dev.kush.security2.repo.CustomerRepo;
import dev.kush.security2.service.ConformationTokenService;
import dev.kush.security2.service.CustomerService;
import dev.kush.security2.service.MailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static dev.kush.security2.models.CustomerRole.USER;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConformationTokenService conformationTokenService;
    private final MailService mailService;

    @Autowired
    public CustomerServiceImpl(CustomerRepo customerRepo, BCryptPasswordEncoder bCryptPasswordEncoder, ConformationTokenService conformationTokenService, MailService mailService) {
        this.customerRepo = customerRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.conformationTokenService = conformationTokenService;
        this.mailService = mailService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return findbyEmail(email);
    }

    private Customer findbyEmail(String email) {
        return customerRepo.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found.")
        );
    }

    @Override
    public String signUp(CustomerDto customerDto) {

        if (customerRepo.existsByEmail(customerDto.email())) {
            throw new RuntimeException("User already exists.");
        }


        Customer customer = new Customer(customerDto.name(),
                customerDto.email(), bCryptPasswordEncoder.encode(customerDto.password()), USER, customerDto.phone());

        customerRepo.save(customer);

        String token = UUID.randomUUID().toString();

        ConformationToken conformationToken = new ConformationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                customer
        );

        conformationTokenService.saveConformationToken(conformationToken);
        String link = "http://localhost:8080/conformation/?token=" + token;
        mailService.sendMail(new EmailDetails(
                customerDto.email(), "<a href=\""
                + link + "\">Activate Now</a>", customerDto.email())
        );
        return "successfully created.";
    }

    @Override
    public String enableUser(String email) {
        customerRepo.enableUser(email);
        return "successfully enabled.";
    }

    @Override
    @Transactional
    public String confirmToken(String token) {
        ConformationToken conformationToken =
                conformationTokenService.getToken(token)
                        .orElseThrow(() -> new IllegalArgumentException("token not found"));

        if (conformationToken.getConfiremdAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        if (conformationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        conformationTokenService.setConfirmedAt(token);
        enableUser(conformationToken.getCustomer().getEmail());
        return "confirmed";
    }


}
