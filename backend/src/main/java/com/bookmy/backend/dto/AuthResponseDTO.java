package com.bookmy.backend.dto;

public class AuthResponseDTO {
    private String token;
    private String type;
    private UtilisateurDTO utilisateur;

    public AuthResponseDTO() {}

    public AuthResponseDTO(String token, String type, UtilisateurDTO utilisateur) {
        this.token = token;
        this.type = type;
        this.utilisateur = utilisateur;
    }

    // Getters et Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public UtilisateurDTO getUtilisateur() { return utilisateur; }
    public void setUtilisateur(UtilisateurDTO utilisateur) { this.utilisateur = utilisateur; }
}