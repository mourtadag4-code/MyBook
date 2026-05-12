package com.bookmy.backend.dto;

import com.bookmy.backend.model.StatutEmprunt;
import java.time.LocalDate;

public class EmpruntDTO {
    private Long id;
    private String membreNom;
    private String livreTitre;
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourEffective;
    private StatutEmprunt statut;
    private Double penalite;

    // Constructeur par défaut
    public EmpruntDTO() {}

    // Constructeur avec tous les champs
    public EmpruntDTO(Long id, String membreNom, String livreTitre, LocalDate dateEmprunt,
                      LocalDate dateRetourPrevue, LocalDate dateRetourEffective, 
                      StatutEmprunt statut, Double penalite) {
        this.id = id;
        this.membreNom = membreNom;
        this.livreTitre = livreTitre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevue = dateRetourPrevue;
        this.dateRetourEffective = dateRetourEffective;
        this.statut = statut;
        this.penalite = penalite;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMembreNom() { return membreNom; }
    public void setMembreNom(String membreNom) { this.membreNom = membreNom; }

    public String getLivreTitre() { return livreTitre; }
    public void setLivreTitre(String livreTitre) { this.livreTitre = livreTitre; }

    public LocalDate getDateEmprunt() { return dateEmprunt; }
    public void setDateEmprunt(LocalDate dateEmprunt) { this.dateEmprunt = dateEmprunt; }

    public LocalDate getDateRetourPrevue() { return dateRetourPrevue; }
    public void setDateRetourPrevue(LocalDate dateRetourPrevue) { this.dateRetourPrevue = dateRetourPrevue; }

    public LocalDate getDateRetourEffective() { return dateRetourEffective; }
    public void setDateRetourEffective(LocalDate dateRetourEffective) { this.dateRetourEffective = dateRetourEffective; }

    public StatutEmprunt getStatut() { return statut; }
    public void setStatut(StatutEmprunt statut) { this.statut = statut; }

    public Double getPenalite() { return penalite; }
    public void setPenalite(Double penalite) { this.penalite = penalite; }
}