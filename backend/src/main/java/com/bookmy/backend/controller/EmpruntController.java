package com.bookmy.backend.controller;

import com.bookmy.backend.model.Emprunt;
import com.bookmy.backend.service.EmpruntService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/emprunts")
public class EmpruntController {

    private final EmpruntService empruntService;

    public EmpruntController(EmpruntService empruntService) {
        this.empruntService = empruntService;
    }

    // GET - Lister tous les emprunts en cours AVEC les détails
    @GetMapping("/en-cours")
    public List<Map<String, Object>> getEmpruntsEnCours() {
        List<Emprunt> emprunts = empruntService.listerEmpruntsEnCours();
        List<Map<String, Object>> result = new ArrayList<>();
        
        for (Emprunt e : emprunts) {
            Map<String, Object> empruntMap = new HashMap<>();
            empruntMap.put("id", e.getId());
            empruntMap.put("dateEmprunt", e.getDateEmprunt());
            empruntMap.put("dateRetourPrevue", e.getDateRetourPrevue());
            empruntMap.put("dateRetourEffective", e.getDateRetourEffective());
            empruntMap.put("statut", e.getStatut());
            empruntMap.put("penalite", e.getPenalite());
            
            // Ajouter les infos du membre
            if (e.getMembre() != null) {
                Map<String, Object> membreMap = new HashMap<>();
                membreMap.put("id", e.getMembre().getId());
                membreMap.put("nom", e.getMembre().getNom());
                membreMap.put("prenom", e.getMembre().getPrenom());
                membreMap.put("email", e.getMembre().getEmail());
                empruntMap.put("membre", membreMap);
            }
            
            // Ajouter les infos du livre
            if (e.getLivre() != null) {
                Map<String, Object> livreMap = new HashMap<>();
                livreMap.put("id", e.getLivre().getId());
                livreMap.put("titre", e.getLivre().getTitre());
                livreMap.put("auteur", e.getLivre().getAuteur());
                livreMap.put("isbn", e.getLivre().getIsbn());
                empruntMap.put("livre", livreMap);
            }
            
            result.add(empruntMap);
        }
        
        return result;
    }

    // GET - Lister tous les emprunts en retard
    @GetMapping("/en-retard")
    public List<Emprunt> getEmpruntsEnRetard() {
        return empruntService.listerEmpruntsEnRetard();
    }

    // GET - Lister les emprunts d'un membre
    @GetMapping("/membre/{membreId}")
    public List<Emprunt> getEmpruntsByMembre(@PathVariable Long membreId) {
        return empruntService.listerEmpruntsParMembre(membreId);
    }

    // GET - Lister les emprunts en cours d'un membre
    @GetMapping("/membre/{membreId}/en-cours")
    public List<Emprunt> getEmpruntsEnCoursByMembre(@PathVariable Long membreId) {
        return empruntService.listerEmpruntsEnCoursParMembre(membreId);
    }

    // GET - Lister les emprunts d'un livre
    @GetMapping("/livre/{livreId}")
    public List<Emprunt> getEmpruntsByLivre(@PathVariable Long livreId) {
        return empruntService.listerEmpruntsParLivre(livreId);
    }

    // POST - Emprunter un livre
    @PostMapping("/emprunter")
    public ResponseEntity<Emprunt> emprunterLivre(
            @RequestParam Long membreId,
            @RequestParam Long livreId) {
        try {
            Emprunt emprunt = empruntService.emprunterLivre(membreId, livreId);
            return new ResponseEntity<>(emprunt, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT - Retourner un livre
    @PutMapping("/retourner/{empruntId}")
    public ResponseEntity<Emprunt> retournerLivre(@PathVariable Long empruntId) {
        try {
            Emprunt emprunt = empruntService.retournerLivre(empruntId);
            return ResponseEntity.ok(emprunt);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}