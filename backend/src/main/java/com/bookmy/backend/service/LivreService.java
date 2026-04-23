package com.bookmy.backend.service;

import com.bookmy.backend.model.Livre;
import com.bookmy.backend.repository.LivreRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LivreService {

    private final LivreRepository livreRepository;

    public LivreService(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    // Ajouter un livre
    public Livre ajouterLivre(Livre livre) {
        livre.setNbExemplairesDisponibles(livre.getNbExemplairesTotal());
        return livreRepository.save(livre);
    }

    // Lister tous les livres
    public List<Livre> listerTous() {
        return livreRepository.findAll();
    }

    // Recherche EXACTE par titre
    public List<Livre> chercherParTitreExact(String titre) {
        return livreRepository.findByTitre(titre);
    }

    // Recherche PARTIELLE par titre (contient le mot)
    public List<Livre> chercherParTitreContenant(String mot) {
        return livreRepository.findByTitre(mot);
    }

    // Recherche par auteur
    public List<Livre> chercherParAuteur(String auteur) {
        return livreRepository.findByAuteur(auteur);
    }

    // Recherche par catégorie
    public List<Livre> chercherParCategorie(String categorie) {
        return livreRepository.findByCategorie(categorie);
    }

    // Recherche par ISBN
    public Livre chercherParIsbn(String isbn) {
        return livreRepository.findByIsbn(isbn);
    }

    // Vérifier si un livre est disponible
    public boolean estDisponible(Long livreId) {
        Livre livre = livreRepository.findById(livreId)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));
        return livre.getNbExemplairesDisponibles() > 0;
    }

    // Obtenir le nombre d'exemplaires disponibles
    public int getNbExemplairesDisponibles(Long livreId) {
        Livre livre = livreRepository.findById(livreId)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));
        return livre.getNbExemplairesDisponibles();
    }
}