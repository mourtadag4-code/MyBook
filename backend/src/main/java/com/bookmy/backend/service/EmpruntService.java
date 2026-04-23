package com.bookmy.backend.service;

import com.bookmy.backend.model.*;
import com.bookmy.backend.repository.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class EmpruntService {

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

        // Vérifier que le membre existe
        Utilisateur membre = utilisateurRepository.findById(membreId)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé"));

        // Vérifier que c'est bien un membre
        if (membre.getRole() != Role.MEMBRE) {
            throw new RuntimeException("Seuls les membres peuvent emprunter des livres");
        }

        // Vérifier que le livre existe
        Livre livre = livreRepository.findById(livreId)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));

        // Vérifier qu'il reste des exemplaires
        if (livre.getNbExemplairesDisponibles() <= 0) {
            throw new RuntimeException("Plus d'exemplaires disponibles pour ce livre");
        }

        // Vérifier que le membre n'a pas déjà 3 emprunts en cours
        List<Emprunt> empruntsEnCours = empruntRepository.findByMembreIdAndStatut(membreId, StatutEmprunt.EN_COURS);
        if (empruntsEnCours.size() >= 3) {
            throw new RuntimeException("Un membre ne peut pas emprunter plus de 3 livres à la fois");
        }

        // Créer l'emprunt
        Emprunt emprunt = new Emprunt();
        emprunt.setMembre(membre);
        emprunt.setLivre(livre);
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setDateRetourPrevue(LocalDate.now().plusDays(14));
        emprunt.setStatut(StatutEmprunt.EN_COURS);
        emprunt.setPenalite(BigDecimal.ZERO);

        // Diminuer le nombre d'exemplaires disponibles
        livre.setNbExemplairesDisponibles(livre.getNbExemplairesDisponibles() - 1);
        livreRepository.save(livre);

        return empruntRepository.save(emprunt);
    }

    // Retourner un livre
    public Emprunt retournerLivre(Long empruntId) {

        Emprunt emprunt = empruntRepository.findById(empruntId)
                .orElseThrow(() -> new RuntimeException("Emprunt non trouvé"));

        // Vérifier que l'emprunt est bien en cours
        if (emprunt.getStatut() != StatutEmprunt.EN_COURS) {
            throw new RuntimeException("Cet emprunt est déjà terminé");
        }

        emprunt.setDateRetourEffective(LocalDate.now());

        // Calculer le retard
        long joursRetard = ChronoUnit.DAYS.between(
                emprunt.getDateRetourPrevue(),
                LocalDate.now()
        );

        if (joursRetard > 0) {
            emprunt.setStatut(StatutEmprunt.EN_RETARD);
            emprunt.setPenalite(BigDecimal.valueOf(joursRetard * 100));
        } else {
            emprunt.setStatut(StatutEmprunt.RETOURNE);
            emprunt.setPenalite(BigDecimal.ZERO);
        }

        // Augmenter les exemplaires disponibles
        Livre livre = emprunt.getLivre();
        livre.setNbExemplairesDisponibles(livre.getNbExemplairesDisponibles() + 1);
        livreRepository.save(livre);

        return empruntRepository.save(emprunt);
    }

    // Lister les emprunts d'un membre
    public List<Emprunt> listerEmpruntsParMembre(Long membreId) {
        utilisateurRepository.findById(membreId)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé"));
        return empruntRepository.findByMembreId(membreId);
    }

    // Lister les emprunts d'un livre
    public List<Emprunt> listerEmpruntsParLivre(Long livreId) {
        livreRepository.findById(livreId)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));
        return empruntRepository.findByLivreId(livreId);
    }

    // Lister les emprunts en cours
    public List<Emprunt> listerEmpruntsEnCours() {
        return empruntRepository.findByStatut(StatutEmprunt.EN_COURS);
    }

    // Lister les emprunts en retard
    public List<Emprunt> listerEmpruntsEnRetard() {
        return empruntRepository.findByStatut(StatutEmprunt.EN_RETARD);
    }

    // Lister les emprunts en cours d'un membre
    public List<Emprunt> listerEmpruntsEnCoursParMembre(Long membreId) {
        utilisateurRepository.findById(membreId)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé"));
        return empruntRepository.findByMembreIdAndStatut(membreId, StatutEmprunt.EN_COURS);
    }
}