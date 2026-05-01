package com.bookmy.backend.controller;

import com.bookmy.backend.model.Livre;
import com.bookmy.backend.service.LivreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/livres")
public class LivreController {

    private final LivreService livreService;

    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }

    // GET - Lister tous les livres
    @GetMapping
    public ResponseEntity<List<Livre>> getAllLivres() {
        return ResponseEntity.ok(livreService.listerTous());
    }

    // GET - Trouver un livre par ID
    @GetMapping("/{id}")
    public ResponseEntity<Livre> getLivreById(@PathVariable Long id) {
        Livre livre = livreService.listerTous().stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (livre != null) {
            return ResponseEntity.ok(livre);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET - Rechercher par titre exact
    @GetMapping("/titre/{titre}")
    public ResponseEntity<List<Livre>> getLivresByTitreExact(@PathVariable String titre) {
        return ResponseEntity.ok(livreService.chercherParTitreExact(titre));
    }

    // GET - Rechercher par titre contenant (recherche partielle)
    @GetMapping("/recherche")
    public ResponseEntity<List<Livre>> getLivresByTitreContenant(@RequestParam String mot) {
        return ResponseEntity.ok(livreService.chercherParTitreContenant(mot));
    }

    // GET - Rechercher par auteur
    @GetMapping("/auteur/{auteur}")
    public ResponseEntity<List<Livre>> getLivresByAuteur(@PathVariable String auteur) {
        return ResponseEntity.ok(livreService.chercherParAuteur(auteur));
    }

    // GET - Rechercher par catégorie
    @GetMapping("/categorie/{categorie}")
    public ResponseEntity<List<Livre>> getLivresByCategorie(@PathVariable String categorie) {
        return ResponseEntity.ok(livreService.chercherParCategorie(categorie));
    }

    // GET - Rechercher par ISBN
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Livre> getLivreByIsbn(@PathVariable String isbn) {
        Livre livre = livreService.chercherParIsbn(isbn);
        if (livre != null) {
            return ResponseEntity.ok(livre);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET - Vérifier si un livre est disponible
    @GetMapping("/{id}/disponible")
    public ResponseEntity<Boolean> isLivreDisponible(@PathVariable Long id) {
        boolean disponible = livreService.estDisponible(id);
        return ResponseEntity.ok(disponible);
    }

    // POST - Ajouter un livre
    @PostMapping
    public ResponseEntity<Livre> addLivre(@RequestBody Livre livre) {
        Livre nouveauLivre = livreService.ajouterLivre(livre);
        return new ResponseEntity<>(nouveauLivre, HttpStatus.CREATED);
    }

    // PUT - Modifier un livre
    @PutMapping("/{id}")
    public ResponseEntity<Livre> modifierLivre(@PathVariable Long id, @RequestBody Livre livre) {
        try {
            Livre livreModifie = livreService.modifierLivre(id, livre);
            return ResponseEntity.ok(livreModifie);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - Supprimer un livre
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerLivre(@PathVariable Long id) {
        try {
            livreService.supprimerLivre(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}