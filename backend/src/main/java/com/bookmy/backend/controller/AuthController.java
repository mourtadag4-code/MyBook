package com.bookmy.backend.controller;

import com.bookmy.backend.model.Utilisateur;
import com.bookmy.backend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")  // Tous les endpoints commencent par /api/auth
public class AuthController {

    private final AuthService authService;

    // Constructeur pour l'injection des dépendances
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint d'inscription
     * POST /api/auth/register
     * Body JSON : { "email": "...", "password": "...", "nom": "...", "prenom": "..." }
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        try {
            // Extraire les données de la requête
            String email = request.get("email");
            String password = request.get("password");
            String nom = request.get("nom");
            String prenom = request.get("prenom");

            // Appeler le service pour créer l'utilisateur
            Utilisateur utilisateur = authService.register(email, password, nom, prenom);

            // Construire la réponse (sans le mot de passe)
            Map<String, Object> response = new HashMap<>();
            response.put("id", utilisateur.getId());
            response.put("email", utilisateur.getEmail());
            response.put("nom", utilisateur.getNom());
            response.put("prenom", utilisateur.getPrenom());
            response.put("role", utilisateur.getRole());

            return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 Created
        } catch (RuntimeException e) {
            // Gestion de l'erreur (email déjà utilisé)
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.CONFLICT); // 409 Conflict
        }
    }

    /**
     * Endpoint de connexion
     * POST /api/auth/login
     * Body JSON : { "email": "...", "password": "..." }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");

            // Appeler le service pour générer le token
            String token = authService.login(email, password);

            // Construire la réponse avec le token
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("type", "Bearer");

            return ResponseEntity.ok(response); // 200 OK
        } catch (Exception e) {
            // Gestion de l'erreur (mauvais identifiants)
            Map<String, String> error = new HashMap<>();
            error.put("error", "Email ou mot de passe incorrect");
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED); // 401 Unauthorized
        }
    }
}