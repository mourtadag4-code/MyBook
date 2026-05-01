package com.bookmy.backend.controller;

import com.bookmy.backend.model.Emprunt;
import com.bookmy.backend.service.EmpruntService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/emprunts")
public class EmpruntController {

    private final EmpruntService empruntService;

    public EmpruntController(EmpruntService empruntService) {
        this.empruntService = empruntService;
    }

    // GET - Lister tous les emprunts en cours
    @GetMapping("/en-cours")
    public List<Emprunt> getEmpruntsEnCours() {
        return empruntService.listerEmpruntsEnCours();
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