package com.bookmy.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
// Classe qui représente la table utilisateur en base de données

@Entity
@Table(name = "utilisateur")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    private String telephone;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDate dateInscription;
    private LocalDate dateNaissance;
    private String adresse;
    private String matricule;
    private LocalDate dateEmbauche;
    private String niveauAcces;

    @OneToMany(mappedBy = "membre", fetch = FetchType.LAZY)
    private List<Emprunt> emprunts;
}