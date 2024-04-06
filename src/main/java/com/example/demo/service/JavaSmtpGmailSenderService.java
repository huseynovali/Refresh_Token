package com.example.demo.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class JavaSmtpGmailSenderService {

    private JavaMailSender emailSender;

    public JavaSmtpGmailSenderService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmail(String toEmail, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-sender-email@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);


        emailSender.send(message);

        System.out.println("Message sent successfully");
    }



}
