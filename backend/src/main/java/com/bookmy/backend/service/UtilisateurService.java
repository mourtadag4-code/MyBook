package com.bookmy.backend.service;

import com.bookmy.backend.model.Role;
import com.bookmy.backend.model.Utilisateur;
import com.bookmy.backend.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    // Créer un membre
    public Utilisateur creerMembre(Utilisateur utilisateur) {
        utilisateur.setDateInscription(LocalDate.now());
        utilisateur.setRole(Role.MEMBRE);
        return utilisateurRepository.save(utilisateur);
    }

    // Créer un bibliothécaire
    public Utilisateur creerBibliothecaire(Utilisateur utilisateur) {
        utilisateur.setDateInscription(LocalDate.now());
        utilisateur.setRole(Role.BIBLIOTHECAIRE);
        return utilisateurRepository.save(utilisateur);
    }

    // Créer un admin
    public Utilisateur creerAdmin(Utilisateur utilisateur) {
        utilisateur.setDateInscription(LocalDate.now());
        utilisateur.setRole(Role.ADMIN);
        return utilisateurRepository.save(utilisateur);
    }

    // Trouver tous les utilisateurs
    public List<Utilisateur> trouverTous() {
        return utilisateurRepository.findAll();
    }

    // Trouver par ID
    public Optional<Utilisateur> trouverParId(Long id) {
        return utilisateurRepository.findById(id);
    }

    // Trouver par email
    public Optional<Utilisateur> trouverParEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    // Trouver les membres uniquement
    public List<Utilisateur> trouverMembres() {
        return utilisateurRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.MEMBRE)
                .toList();
    }

    // Supprimer un utilisateur
    public void supprimerUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }
    // Modifier un membre
public Utilisateur modifierMembre(Long id, Utilisateur membreModifie) {
    Utilisateur membre = utilisateurRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Membre non trouvé"));
    
    membre.setNom(membreModifie.getNom());
    membre.setPrenom(membreModifie.getPrenom());
    membre.setEmail(membreModifie.getEmail());
    membre.setTelephone(membreModifie.getTelephone());
    membre.setAdresse(membreModifie.getAdresse());
    membre.setDateNaissance(membreModifie.getDateNaissance());
    // Le rôle reste MEMBRE (on ne modifie pas le rôle)
    
    return utilisateurRepository.save(membre);
}
}