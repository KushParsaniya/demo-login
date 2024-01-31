package dev.kush.security2.service.impl;

import dev.kush.security2.models.EmailDetails;
import dev.kush.security2.service.MailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Value(value = "${spring.mail.username}")
    private String sender;

    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Override
    public String sendMail(EmailDetails emailDetails) {
        try {
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage);


            helper.setFrom(sender);
            helper.setTo(emailDetails.getRecipient());
            helper.setSubject(emailDetails.getSubject());
            helper.setText(emailDetails.getMsgBody(),true);

            javaMailSender.send(mailMessage);

            return "email sent successfully.";
        } catch (Exception e){
            return "error sending email.";
        }
    }
}
