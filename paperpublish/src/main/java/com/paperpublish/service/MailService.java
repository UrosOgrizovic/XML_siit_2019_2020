package com.paperpublish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    @Async
    public boolean sendEmail(String emailTo, String paperName, String status) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            String text = String.format("Your papers: " + paperName + ", status has been updated to: " + status);
            try {
                mimeMessage.setContent(text, "text/html");
                helper.setTo(emailTo);
                helper.setSubject("Paper status update");
                helper.setFrom(env.getProperty("spring.mail.username"));

                javaMailSender.send(mimeMessage);
                return true;
            } catch (MessagingException e) {
                e.printStackTrace();
                return false;
            }
        } catch (MailException e) {
            System.out.println(e);
            System.err.println("SENDING EMAIL FAILED.");
            return false;
        }
    }
}
