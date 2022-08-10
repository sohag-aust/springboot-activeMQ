package com.shohag.ActiveMQ_SendEmail.services;

import com.shohag.ActiveMQ_SendEmail.config.EmailConfig;
import com.shohag.ActiveMQ_SendEmail.message.ActiveMQMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    private EmailConfig emailConfig;

    public MailService(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(emailConfig.getHost());
        javaMailSender.setPort(emailConfig.getPort());
        javaMailSender.setUsername(emailConfig.getUsername());
        javaMailSender.setPassword(emailConfig.getPassword());
        return javaMailSender;
    }

    public void sendEmail(ActiveMQMessage message) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(message.getMailFrom());
        mail.setTo(message.getMailTo());
        mail.setSubject(message.getSubject());
        mail.setText(message.getText());

        getJavaMailSender().send(mail);
    }

    public void sendEmailWithAttachment(ActiveMQMessage message) throws MailException, MessagingException {
        MimeMessage mimeMessage = getJavaMailSender().createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom(message.getMailFrom());
        helper.setTo(message.getMailTo());
        helper.setSubject(message.getSubject());
        helper.setText(message.getText());

        ClassPathResource classPathResource = new ClassPathResource("spring_tutorial.pdf");
        helper.addAttachment(classPathResource.getFilename(), classPathResource);

        getJavaMailSender().send(mimeMessage);
    }
}
