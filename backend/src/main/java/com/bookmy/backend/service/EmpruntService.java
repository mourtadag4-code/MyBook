package com.bookmy.backend.service;

import com.bookmy.backend.model.*;
import com.bookmy.backend.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class EmpruntService {

    private static final Logger log = LoggerFactory.getLogger(EmpruntService.class);

    private final EmpruntRepository empruntRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final LivreRepository livreRepository;

    public EmpruntService(EmpruntRepository empruntRepository,
                          UtilisateurRepository utilisateurRepository,
                          LivreRepository livreRepository) {
        this.empruntRepository = empruntRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.livreRepository = livreRepository;
    }

    // Un membre emprunte un livre
    public Emprunt emprunterLivre(Long membreId, Long livreId) {
        log.info("📚 Tentative d'emprunt - Membre ID: {}, Livre ID: {}", membreId, livreId);

        Utilisateur membre = utilisateurRepository.findById(membreId)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé"));

        if (membre.getRole() != Role.MEMBRE) {
            log.warn("⚠️ Tentative d'emprunt par un non-membre - Utilisateur ID: {}, Rôle: {}", membreId, membre.getRole());
            throw new RuntimeException("Seuls les membres peuvent emprunter des livres");
        }

        Livre livre = livreRepository.findById(livreId)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));

        if (livre.getNbExemplairesDisponibles() <= 0) {
            log.warn("⚠️ Tentative d'emprunt d'un livre indisponible - Livre ID: {}, Titre: {}", livreId, livre.getTitre());
            throw new RuntimeException("Plus d'exemplaires disponibles pour ce livre");
        }

        List<Emprunt> empruntsEnCours = empruntRepository.findByMembreIdAndStatut(membreId, StatutEmprunt.EN_COURS);
        if (empruntsEnCours.size() >= 3) {
            log.warn("⚠️ Membre ID: {} a déjà {} emprunts en cours (max 3)", membreId, empruntsEnCours.size());
            throw new RuntimeException("Un membre ne peut pas emprunter plus de 3 livres à la fois");
        }

        Emprunt emprunt = new Emprunt();
        emprunt.setMembre(membre);
        emprunt.setLivre(livre);
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setDateRetourPrevue(LocalDate.now().plusDays(14));
        emprunt.setStatut(StatutEmprunt.EN_COURS);
        emprunt.setPenalite(BigDecimal.ZERO);

        livre.setNbExemplairesDisponibles(livre.getNbExemplairesDisponibles() - 1);
        livreRepository.save(livre);

        Emprunt savedEmprunt = empruntRepository.save(emprunt);
        log.info("✅ Emprunt réussi - ID: {}, Membre: {}, Livre: {}", savedEmprunt.getId(), membre.getNom(), livre.getTitre());

        return savedEmprunt;
    }

    // Retourner un livre
    public Emprunt retournerLivre(Long empruntId) {
        log.info("🔄 Tentative de retour - Emprunt ID: {}", empruntId);

        Emprunt emprunt = empruntRepository.findById(empruntId)
                .orElseThrow(() -> new RuntimeException("Emprunt non trouvé"));

        if (emprunt.getStatut() != StatutEmprunt.EN_COURS) {
            log.warn("⚠️ Tentative de retour d'un emprunt déjà terminé - ID: {}, Statut: {}", empruntId, emprunt.getStatut());
            throw new RuntimeException("Cet emprunt est déjà terminé");
        }

        emprunt.setDateRetourEffective(LocalDate.now());

        long joursRetard = ChronoUnit.DAYS.between(
                emprunt.getDateRetourPrevue(),
                LocalDate.now()
        );

        if (joursRetard > 0) {
            emprunt.setStatut(StatutEmprunt.EN_RETARD);
            emprunt.setPenalite(BigDecimal.valueOf(joursRetard * 100));
            log.info("⚠️ Retour en retard - Emprunt ID: {}, Jours de retard: {}, Pénalité: {} FCFA", 
                    empruntId, joursRetard, joursRetard * 100);
        } else {
            emprunt.setStatut(StatutEmprunt.RETOURNE);
            emprunt.setPenalite(BigDecimal.ZERO);
            log.info("✅ Retour à temps - Emprunt ID: {}", empruntId);
        }

        Livre livre = emprunt.getLivre();
        livre.setNbExemplairesDisponibles(livre.getNbExemplairesDisponibles() + 1);
        livreRepository.save(livre);

        Emprunt savedEmprunt = empruntRepository.save(emprunt);
        log.info("✅ Retour terminé - Emprunt ID: {}, Livre: {}", savedEmprunt.getId(), livre.getTitre());

        return savedEmprunt;
    }

    // Lister les emprunts d'un membre
    public List<Emprunt> listerEmpruntsParMembre(Long membreId) {
        log.info("📋 Consultation des emprunts du membre ID: {}", membreId);
        utilisateurRepository.findById(membreId)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé"));
        return empruntRepository.findByMembreId(membreId);
    }

    // Lister les emprunts d'un livre
    public List<Emprunt> listerEmpruntsParLivre(Long livreId) {
        log.info("📋 Consultation des emprunts du livre ID: {}", livreId);
        livreRepository.findById(livreId)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));
        return empruntRepository.findByLivreId(livreId);
    }

    // Lister les emprunts en cours
    public List<Emprunt> listerEmpruntsEnCours() {
        log.info("📋 Consultation de tous les emprunts en cours");
        return empruntRepository.findByStatut(StatutEmprunt.EN_COURS);
    }

    // Lister les emprunts en retard
    public List<Emprunt> listerEmpruntsEnRetard() {
        log.info("📋 Consultation de tous les emprunts en retard");
        return empruntRepository.findByStatut(StatutEmprunt.EN_RETARD);
    }

    // Lister les emprunts en cours d'un membre
    public List<Emprunt> listerEmpruntsEnCoursParMembre(Long membreId) {
        log.info("📋 Consultation des emprunts en cours du membre ID: {}", membreId);
        utilisateurRepository.findById(membreId)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé"));
        return empruntRepository.findByMembreIdAndStatut(membreId, StatutEmprunt.EN_COURS);
    }

    // Vérifier si un membre a des emprunts en retard
    public boolean verifierRetardsMembre(Long membreId) {
        log.info("🔍 Vérification des retards pour le membre ID: {}", membreId);
        utilisateurRepository.findById(membreId)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé"));
        
        List<Emprunt> empruntsEnRetard = empruntRepository.findByMembreIdAndStatut(membreId, StatutEmprunt.EN_RETARD);
        
        return !empruntsEnRetard.isEmpty();
    }

    // Calculer la pénalité d'un emprunt (sans modifier le statut)
    public BigDecimal calculerPenalite(Emprunt emprunt) {
        if (emprunt.getDateRetourEffective() == null) {
            return BigDecimal.ZERO;
        }
        
        long joursRetard = ChronoUnit.DAYS.between(
            emprunt.getDateRetourPrevue(),
            emprunt.getDateRetourEffective()
        );
        
        if (joursRetard > 0) {
            return BigDecimal.valueOf(joursRetard * 100);
        }
        return BigDecimal.ZERO;
    }
}