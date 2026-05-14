package com.bookmy.backend.controller;

import com.bookmy.backend.model.Utilisateur;
import com.bookmy.backend.service.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurService.trouverTous();
    }

    @GetMapping("/membres")
    public List<Utilisateur> getAllMembres() {
        return utilisateurService.trouverMembres();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Long id) {
        Optional<Utilisateur> utilisateur = utilisateurService.trouverParId(id);
        return utilisateur.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Utilisateur> getUtilisateurByEmail(@PathVariable String email) {
        Optional<Utilisateur> utilisateur = utilisateurService.trouverParEmail(email);
        return utilisateur.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/membres")
    public ResponseEntity<Utilisateur> createMembre(@RequestBody Utilisateur utilisateur) {
        Utilisateur nouveauMembre = utilisateurService.creerMembre(utilisateur);
        return new ResponseEntity<>(nouveauMembre, HttpStatus.CREATED);
    }

    @PostMapping("/bibliothecaires")
    public ResponseEntity<Utilisateur> createBibliothecaire(@RequestBody Utilisateur utilisateur) {
        Utilisateur nouveauBibliothecaire = utilisateurService.creerBibliothecaire(utilisateur);
        return new ResponseEntity<>(nouveauBibliothecaire, HttpStatus.CREATED);
    }

    @PostMapping("/admins")
    public ResponseEntity<Utilisateur> createAdmin(@RequestBody Utilisateur utilisateur) {
        Utilisateur nouvelAdmin = utilisateurService.creerAdmin(utilisateur);
        return new ResponseEntity<>(nouvelAdmin, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.supprimerUtilisateur(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/membres/{id}")
    public ResponseEntity<Utilisateur> modifierMembre(@PathVariable Long id, @RequestBody Utilisateur membre) {
        try {
            Utilisateur membreModifie = utilisateurService.modifierMembre(id, membre);
            return ResponseEntity.ok(membreModifie);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}