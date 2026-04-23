package com.bookmy.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

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

    // Plusieurs emprunts → 1 utilisateur
    @ManyToOne(fetch = FetchType.LAZY)  // Plusieurs emprunts → 1 utilisateur (chargé à la demande)
    @JoinColumn(name = "membre_id", nullable = false)
    private Utilisateur membre;

    // Plusieurs emprunts → 1 livre
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livre_id", nullable = false)
    private Livre livre;

    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourEffective;

    @Enumerated(EnumType.STRING)
    private StatutEmprunt statut;

    private BigDecimal penalite = BigDecimal.ZERO;
}