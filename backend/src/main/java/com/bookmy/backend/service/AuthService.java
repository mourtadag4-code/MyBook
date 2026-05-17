package com.bookmy.backend.service;


import com.bookmy.backend.config.JwtUtil;
import com.bookmy.backend.model.Role;
import com.bookmy.backend.model.Utilisateur;
import com.bookmy.backend.repository.UtilisateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UtilisateurRepository utilisateurRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Utilisateur register(String email, String password, String nom, String prenom) {
        if (utilisateurRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(email);
        utilisateur.setPassword(passwordEncoder.encode(password));
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setRole(Role.MEMBRE);
        utilisateur.setDateInscription(LocalDate.now());

        return utilisateurRepository.save(utilisateur);
    }

    public String login(String email, String password) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));

        if (!passwordEncoder.matches(password, utilisateur.getPassword())) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        // Créer un UserDetails temporaire pour générer le token
        org.springframework.security.core.userdetails.User userDetails =
            new org.springframework.security.core.userdetails.User(
                utilisateur.getEmail(),
                utilisateur.getPassword(),
                java.util.Collections.singletonList(
                    new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().name())
                )
            );

        // 🔥 MODIFICATION ICI : Passer l'ID de l'utilisateur
        return jwtUtil.generateToken(userDetails, utilisateur.getId());
    }
}