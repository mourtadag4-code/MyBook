package com.bookmy.backend.controller;

import com.bookmy.backend.service.StatistiqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/statistiques")
public class StatistiqueController {

    private final StatistiqueService statistiqueService;

    // Constructeur pour l'injection des dépendances
    public StatistiqueController(StatistiqueService statistiqueService) {
        this.statistiqueService = statistiqueService;
    }

    /**
     * GET /api/statistiques/total-livres
     * Retourne le nombre total de livres
     */
    @GetMapping("/total-livres")
    public ResponseEntity<Map<String, Long>> getTotalLivres() {
        Map<String, Long> response = Map.of("total", statistiqueService.getTotalLivres());
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/statistiques/total-membres
     * Retourne le nombre total de membres
     */
    @GetMapping("/total-membres")
    public ResponseEntity<Map<String, Long>> getTotalMembres() {
        Map<String, Long> response = Map.of("total", statistiqueService.getTotalMembres());
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/statistiques/emprunts-en-cours
     * Retourne le nombre d'emprunts en cours
     */
    @GetMapping("/emprunts-en-cours")
    public ResponseEntity<Map<String, Long>> getEmpruntsEnCours() {
        Map<String, Long> response = Map.of("total", statistiqueService.getEmpruntsEnCours());
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/statistiques/emprunts-en-retard
     * Retourne le nombre d'emprunts en retard
     */
    @GetMapping("/emprunts-en-retard")
    public ResponseEntity<Map<String, Long>> getEmpruntsEnRetard() {
        Map<String, Long> response = Map.of("total", statistiqueService.getEmpruntsEnRetard());
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/statistiques/livres-plus-empruntes
     * Retourne le top 5 des livres les plus empruntés
     */
    @GetMapping("/livres-plus-empruntes")
    public ResponseEntity<?> getLivresPlusEmpruntes() {
        return ResponseEntity.ok(statistiqueService.getLivresPlusEmpruntes());
    }

    /**
     * GET /api/statistiques/toutes
     * Retourne toutes les statistiques en une seule requête
     */
    @GetMapping("/toutes")
    public ResponseEntity<Map<String, Object>> getToutesLesStatistiques() {
        return ResponseEntity.ok(statistiqueService.getToutesLesStatistiques());
    }
}