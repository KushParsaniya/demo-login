package dev.kush.security2.service;

import dev.kush.security2.models.EmailDetails;

public interface MailService {

    String sendMail(EmailDetails emailDetails);
}
