package com.example.IV_backend.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
@Service
@AllArgsConstructor
@NoArgsConstructor
public class EmailServices {


    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;


    public void sendVerificationMail(String email, String verificationCode) {
        String subject = "Email verification";
        String path = "req/signup/verify";
        String message = "Click the button below to verify your email";
        SendEmail(email, verificationCode,subject, path, message);
    }

    public void sendForgotPasswordMail(String email, String resetToken) {
        String subject = "Pasword Reset Request";
        String path = "req/reset-password";
        String message = "Click the button to reset your password";
        SendEmail(email, resetToken,subject, path, message);
    }

    private void SendEmail(String email, String token, String subject, String path, String message) {
        try {
            String actionUrl = "http://localhost:5173/" + path + "?token=" + token;

            String buttonLabel = subject.toLowerCase().contains("reset") ? "Reset Password" : "Verify Email";

            String content = """
        <html>
          <body style="font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 40px;">
            <div style="max-width: 600px; margin: auto; background: white; padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0,0,0,0.1);">
              <h2 style="color: #2e6c80;">%s</h2>
              <p>%s</p>
              <a href="%s" style="display: inline-block; padding: 12px 25px; background-color: #4CAF50; 
                 color: white; text-decoration: none; border-radius: 5px; font-weight: bold;">
                 %s
              </a>
              <p style="margin-top: 20px;">If the button doesn't work, copy and paste this link into your browser:</p>
              <p><a href="%s">%s</a></p>
            </div>
          </body>
        </html>
        """.formatted(subject, message, actionUrl, buttonLabel, actionUrl, actionUrl);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(email);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }


}
