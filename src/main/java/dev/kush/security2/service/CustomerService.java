package dev.kush.security2.service;

import dev.kush.security2.models.CustomerDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomerService extends UserDetailsService {

    String signUp(CustomerDto customerDto);

    String enableUser(String email);

    String confirmToken(String token);
}
