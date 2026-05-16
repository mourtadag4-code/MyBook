package com.bookmy.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "emprunt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Emprunt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membre_id", nullable = false)
    @JsonIgnore  // ← AJOUTE CETTE LIGNE
    private Utilisateur membre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livre_id", nullable = false)
    @JsonIgnore  // ← AJOUTE CETTE LIGNE
    private Livre livre;

    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourEffective;

    @Enumerated(EnumType.STRING)
    private StatutEmprunt statut;

    private BigDecimal penalite = BigDecimal.ZERO;
}