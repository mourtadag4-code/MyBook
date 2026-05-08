package com.bookmy.backend.service;

import com.bookmy.backend.config.JwtUtil;
import com.bookmy.backend.model.Role;
import com.bookmy.backend.model.Utilisateur;
import com.bookmy.backend.repository.UtilisateurRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthService {

    // Dépendances injectées par le constructeur
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    // Constructeur pour l'injection des dépendances
    public AuthService(UtilisateurRepository utilisateurRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtUtil jwtUtil) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Inscription d'un nouveau membre
     * @param email Email unique
     * @param password Mot de passe (sera hashé)
     * @param nom Nom de l'utilisateur
     * @param prenom Prénom de l'utilisateur
     * @return L'utilisateur créé
     */
    public Utilisateur register(String email, String password, String nom, String prenom) {
        // Vérifier si l'email existe déjà
        if (utilisateurRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        // Créer un nouvel utilisateur
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(email);
        // Hacher le mot de passe avant de le stocker
        utilisateur.setPassword(passwordEncoder.encode(password));
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setRole(Role.MEMBRE); // Rôle par défaut
        utilisateur.setDateInscription(LocalDate.now()); // Date d'inscription automatique

        // Sauvegarder en base de données
        return utilisateurRepository.save(utilisateur);
    }

    /**
     * Connexion d'un utilisateur
     * @param email Email de l'utilisateur
     * @param password Mot de passe
     * @return Token JWT
     */
    public String login(String email, String password) {
        // Authentifier l'utilisateur
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // Récupérer les détails de l'utilisateur authentifié
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Générer un token JWT
        return jwtUtil.generateToken(userDetails);
    }
}