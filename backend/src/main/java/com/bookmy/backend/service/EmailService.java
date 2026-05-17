package com.bookmy.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOverdueNotification(String to, String nom, int joursRetard) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("⚠️ Retard dans le retour de votre livre");
            message.setText("Bonjour " + nom + ",\n\n" +
                    "Vous avez un retard de " + joursRetard + " jour(s) dans le retour de votre livre.\n" +
                    "Veuillez le retourner dès que possible.\n\n" +
                    "Cordialement,\n" +
                    "L'équipe MyBook");
            mailSender.send(message);
            log.info("📧 Email de retard envoyé à {}", to);
        } catch (Exception e) {
            log.error("❌ Erreur lors de l'envoi de l'email à {}: {}", to, e.getMessage());
        }
    }
}