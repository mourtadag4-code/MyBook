package com.bookmy.backend.service;

import com.bookmy.backend.model.Role;
import com.bookmy.backend.model.Utilisateur;
import com.bookmy.backend.repository.UtilisateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    private static final Logger log = LoggerFactory.getLogger(UtilisateurService.class);

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    // Créer un membre
    public Utilisateur creerMembre(Utilisateur utilisateur) {
        log.info("👤 Création d'un nouveau membre - Email: {}, Nom: {}", utilisateur.getEmail(), utilisateur.getNom());
        utilisateur.setDateInscription(LocalDate.now());
        utilisateur.setRole(Role.MEMBRE);
        Utilisateur savedUser = utilisateurRepository.save(utilisateur);
        log.info("✅ Membre créé avec succès - ID: {}", savedUser.getId());
        return savedUser;
    }

    // Créer un bibliothécaire
    public Utilisateur creerBibliothecaire(Utilisateur utilisateur) {
        log.info("📚 Création d'un nouveau bibliothécaire - Email: {}, Nom: {}", utilisateur.getEmail(), utilisateur.getNom());
        utilisateur.setDateInscription(LocalDate.now());
        utilisateur.setRole(Role.BIBLIOTHECAIRE);
        Utilisateur savedUser = utilisateurRepository.save(utilisateur);
        log.info("✅ Bibliothécaire créé avec succès - ID: {}", savedUser.getId());
        return savedUser;
    }

    // Créer un admin
    public Utilisateur creerAdmin(Utilisateur utilisateur) {
        log.info("👑 Création d'un nouvel administrateur - Email: {}, Nom: {}", utilisateur.getEmail(), utilisateur.getNom());
        utilisateur.setDateInscription(LocalDate.now());
        utilisateur.setRole(Role.ADMIN);
        Utilisateur savedUser = utilisateurRepository.save(utilisateur);
        log.info("✅ Administrateur créé avec succès - ID: {}", savedUser.getId());
        return savedUser;
    }

    // Trouver tous les utilisateurs
    public List<Utilisateur> trouverTous() {
        log.info("📋 Consultation de la liste de tous les utilisateurs");
        return utilisateurRepository.findAll();
    }

    // Trouver par ID
    public Optional<Utilisateur> trouverParId(Long id) {
        log.info("🔍 Recherche d'utilisateur par ID: {}", id);
        return utilisateurRepository.findById(id);
    }

    // Trouver par email
    public Optional<Utilisateur> trouverParEmail(String email) {
        log.info("🔍 Recherche d'utilisateur par email: {}", email);
        return utilisateurRepository.findByEmail(email);
    }

    // Trouver les membres uniquement
    public List<Utilisateur> trouverMembres() {
        log.info("📋 Consultation de la liste de tous les membres");
        return utilisateurRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.MEMBRE)
                .toList();
    }

    // Supprimer un utilisateur
    public void supprimerUtilisateur(Long id) {
        log.info("🗑️ Suppression de l'utilisateur ID: {}", id);
        utilisateurRepository.deleteById(id);
        log.info("✅ Utilisateur supprimé avec succès - ID: {}", id);
    }

    // Modifier un membre
    public Utilisateur modifierMembre(Long id, Utilisateur membreModifie) {
        log.info("✏️ Modification du membre ID: {}", id);
        Utilisateur membre = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé"));
        
        membre.setNom(membreModifie.getNom());
        membre.setPrenom(membreModifie.getPrenom());
        membre.setEmail(membreModifie.getEmail());
        membre.setTelephone(membreModifie.getTelephone());
        membre.setAdresse(membreModifie.getAdresse());
        membre.setDateNaissance(membreModifie.getDateNaissance());
        
        Utilisateur savedUser = utilisateurRepository.save(membre);
        log.info("✅ Membre modifié avec succès - ID: {}, Nom: {}", savedUser.getId(), savedUser.getNom());
        return savedUser;
    }
}