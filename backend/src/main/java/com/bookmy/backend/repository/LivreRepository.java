package com.bookmy.backend.repository;

import com.bookmy.backend.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LivreRepository extends JpaRepository<Livre, Long> {
    
    // Rechercher des livres par TITRE (exact)
    List<Livre> findByTitre(String titre);
    
    // Rechercher des livres par AUTEUR
    List<Livre> findByAuteur(String auteur);
    
    // Rechercher des livres par CATEGORIE
    List<Livre> findByCategorie(String categorie);
    
    // Rechercher des livres par ISBN (unique)
    Livre findByIsbn(String isbn);
}