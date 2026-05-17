package com.bookmy.backend.config;

import com.bookmy.backend.model.Emprunt;
import com.bookmy.backend.model.StatutEmprunt;
import com.bookmy.backend.model.Utilisateur;
import com.bookmy.backend.repository.EmpruntRepository;
import com.bookmy.backend.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class EmailScheduler {

    private static final Logger log = LoggerFactory.getLogger(EmailScheduler.class);
    private final EmpruntRepository empruntRepository;
    private final EmailService emailService;

    public EmailScheduler(EmpruntRepository empruntRepository, EmailService emailService) {
        this.empruntRepository = empruntRepository;
        this.emailService = emailService;
    }

    // Tous les jours à 8h00
    @Scheduled(cron = "0 0 8 * * *")
    public void verifierRetardsEtEnvoyerEmails() {
        log.info("📧 Vérification des retards en cours...");
        
        List<Emprunt> empruntsRetard = empruntRepository.findByStatut(StatutEmprunt.EN_RETARD);
        
        for (Emprunt emprunt : empruntsRetard) {
            Utilisateur membre = emprunt.getMembre();
            long joursRetard = ChronoUnit.DAYS.between(
                emprunt.getDateRetourPrevue(),
                LocalDate.now()
            );
            
            emailService.sendOverdueNotification(
                membre.getEmail(),
                membre.getNom(),
                (int) joursRetard
            );
        }
        
        log.info("✅ Vérification terminée. {} email(s) envoyé(s)", empruntsRetard.size());
    }
}