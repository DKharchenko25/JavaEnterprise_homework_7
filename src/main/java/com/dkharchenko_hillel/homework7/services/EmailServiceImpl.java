package com.dkharchenko_hillel.homework7.services;

import com.dkharchenko_hillel.homework7.models.Person;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendRegistrationEmail(Person person) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(person.getEmail());
            helper.setSubject("OnlineShop Registration");
            helper.setText("<h3>Dear, " + person.getFirstName() + "!</h3><br>" +
                    "<h3>You've successfully completed registration for OnlineShop!</h3><br>" +
                    "<i><h4>Follow the link to log in and start shopping:</h4></i> " +
                    "<a href=\"http://localhost:8080/login\" target=\"_blank\">Log in</a>", true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
