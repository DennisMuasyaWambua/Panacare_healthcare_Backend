package com.panacare.panabeans.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomEmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    public CustomEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
        // Check if JavaMailSender is injected correctly
        System.out.println("JavaMailSender initialized: " + (javaMailSender != null));
    }

    @Async
    public void sendEmail(String to, String subject, String body, boolean isHtml) {
        log.info("Current Thread: {}", Thread.currentThread().getName());
        log.info("This is the boolean value: {}", isHtml);
        log.info("This is the body: {}", body);
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            helper.setFrom(fromEmail); // Use value from application.properties
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

}