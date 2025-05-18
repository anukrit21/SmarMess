package com.demoApp.mess.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Slf4j
public class EmailService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final String fromEmail;
    private final String frontendUrl;

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine, @Value("${spring.mail.username}") String fromEmail, @Value("${app.frontend-url}") String frontendUrl) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.fromEmail = fromEmail;
        this.frontendUrl = frontendUrl;
    }

    @Async
    public void sendPasswordResetEmail(String to, String name, String resetLink) {
        try {
            log.info("Sending password reset email to: {}", to);
            
            // Prepare the context for the template
            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("resetLink", resetLink);
            context.setVariable("frontendUrl", frontendUrl);
            
            // Process the template
            String htmlContent = templateEngine.process("password-reset", context);
            
            // Create the email message
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject("Password Reset Request");
            helper.setText(htmlContent, true);
            
            // Send the email
            mailSender.send(message);
            log.info("Password reset email sent to: {}", to);
            
        } catch (MessagingException e) {
            log.error("Failed to send password reset email to: {}", to, e);
        }
    }
    
    @Async
    public void sendWelcomeEmail(String to, String name) {
        try {
            log.info("Sending welcome email to: {}", to);
            
            // Prepare the context for the template
            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("frontendUrl", frontendUrl);
            
            // Process the template
            String htmlContent = templateEngine.process("welcome", context);
            
            // Create the email message
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject("Welcome to Mess App");
            helper.setText(htmlContent, true);
            
            // Send the email
            mailSender.send(message);
            log.info("Welcome email sent to: {}", to);
            
        } catch (MessagingException e) {
            log.error("Failed to send welcome email to: {}", to, e);
        }
    }
    
    @Async
    public void sendSubscriptionConfirmationEmail(String to, String customerName, String subject, String messName) {
        try {
            log.info("Sending subscription confirmation email to: {}", to);
            
            // Prepare the context for the template
            Context context = new Context();
            context.setVariable("name", customerName);
            context.setVariable("messName", messName);
            context.setVariable("frontendUrl", frontendUrl);
            
            // Process the template
            String htmlContent = templateEngine.process("subscription-confirmation", context);
            
            // Create the email message
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            
            // Send the email
            mailSender.send(message);
            log.info("Subscription confirmation email sent to: {}", to);
            
        } catch (MessagingException e) {
            log.error("Failed to send subscription confirmation email to: {}", to, e);
        }
    }
}
