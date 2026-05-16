package com.bookmy.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "livre")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String auteur;

    @Column(unique = true)
    private String isbn;

    private String categorie;

    private int nbExemplairesTotal;
    private int nbExemplairesDisponibles;

    @OneToMany(mappedBy = "livre", fetch = FetchType.LAZY)
    @JsonIgnore  // ← AJOUTE CETTE LIGNE
    private List<Emprunt> emprunts;
}