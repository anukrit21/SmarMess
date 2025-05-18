package com.demoApp.otp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

/**
 * Service for sending emails
 */
@Service
@RequiredArgsConstructor
public class EmailService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    
    @Value("${spring.mail.username:noreply@demoapp.com}")
    private String fromEmail;
    
    @Value("${app.name:DemoApp}")
    private String appName;
    
    /**
     * Send a simple email with OTP
     * @param to recipient email
     * @param otp OTP code
     */
    public void sendOtpEmail(String to, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Your OTP");
            message.setText("Your OTP is: " + otp);
            mailSender.send(message);
            log.debug("OTP email sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send OTP email to: {}", to, e);
            throw new RuntimeException("Failed to send OTP email", e);
        }
    }
    
    /**
     * Send HTML email with OTP using Thymeleaf template if available
     * @param to recipient email
     * @param otp OTP code
     * @throws MessagingException if message cannot be created
     */
    public void sendOtpHtmlEmail(String to, String otp) throws MessagingException {
        if (templateEngine == null) {
            // Fall back to simple email if template engine not configured
            sendOtpEmail(to, otp);
            return;
        }
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        Context context = new Context();
        context.setVariable("otp", otp);
        context.setVariable("appName", appName);
        
        String htmlContent = templateEngine.process("otp-template", context);
        
        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(appName + " - Your Verification Code");
        helper.setText(htmlContent, true);
        
        mailSender.send(message);
    }

    public void sendEmail(String to, String subject, String templateName, Map<String, Object> variables) {
        try {
            Context context = new Context();
            variables.forEach(context::setVariable);
            
            String content = templateEngine.process(templateName, context);
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            
            mailSender.send(message);
            log.info("Email sent successfully to: {}", to);
        } catch (MessagingException e) {
            log.error("Failed to send email to: {}", to, e);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
