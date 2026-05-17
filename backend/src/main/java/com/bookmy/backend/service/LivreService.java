package com.bookmy.backend.service;

import com.bookmy.backend.model.Livre;
import com.bookmy.backend.repository.LivreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LivreService {

    private static final Logger log = LoggerFactory.getLogger(LivreService.class);

    private final LivreRepository livreRepository;

    public LivreService(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    // Ajouter un livre
    public Livre ajouterLivre(Livre livre) {
        log.info("📚 Ajout d'un nouveau livre - Titre: {}, Auteur: {}, ISBN: {}", livre.getTitre(), livre.getAuteur(), livre.getIsbn());
        livre.setNbExemplairesDisponibles(livre.getNbExemplairesTotal());
        Livre savedLivre = livreRepository.save(livre);
        log.info("✅ Livre ajouté avec succès - ID: {}", savedLivre.getId());
        return savedLivre;
    }

    // Lister tous les livres
    public List<Livre> listerTous() {
        log.info("📋 Consultation de la liste de tous les livres");
        return livreRepository.findAll();
    }

    // Recherche EXACTE par titre
    public List<Livre> chercherParTitreExact(String titre) {
        log.info("🔍 Recherche de livres par titre exact: {}", titre);
        return livreRepository.findByTitre(titre);
    }

    // Recherche PARTIELLE par titre (contient le mot)
    public List<Livre> chercherParTitreContenant(String mot) {
        log.info("🔍 Recherche de livres par mot-clé: {}", mot);
        return livreRepository.findByTitre(mot);
    }

    // Recherche par auteur
    public List<Livre> chercherParAuteur(String auteur) {
        log.info("🔍 Recherche de livres par auteur: {}", auteur);
        return livreRepository.findByAuteur(auteur);
    }

    // Recherche par catégorie
    public List<Livre> chercherParCategorie(String categorie) {
        log.info("🔍 Recherche de livres par catégorie: {}", categorie);
        return livreRepository.findByCategorie(categorie);
    }

    // Recherche par ISBN
    public Livre chercherParIsbn(String isbn) {
        log.info("🔍 Recherche de livre par ISBN: {}", isbn);
        return livreRepository.findByIsbn(isbn);
    }

    // Vérifier si un livre est disponible
    public boolean estDisponible(Long livreId) {
        log.info("🔍 Vérification de disponibilité du livre ID: {}", livreId);
        Livre livre = livreRepository.findById(livreId)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));
        boolean disponible = livre.getNbExemplairesDisponibles() > 0;
        log.info("📖 Livre ID: {} - Disponible: {}", livreId, disponible);
        return disponible;
    }

    // Obtenir le nombre d'exemplaires disponibles
    public int getNbExemplairesDisponibles(Long livreId) {
        Livre livre = livreRepository.findById(livreId)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));
        return livre.getNbExemplairesDisponibles();
    }

    // Modifier un livre
    public Livre modifierLivre(Long id, Livre nouveauLivre) {
        log.info("✏️ Modification du livre ID: {}", id);
        Livre livreExistant = livreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));
        
        livreExistant.setTitre(nouveauLivre.getTitre());
        livreExistant.setAuteur(nouveauLivre.getAuteur());
        livreExistant.setIsbn(nouveauLivre.getIsbn());
        livreExistant.setCategorie(nouveauLivre.getCategorie());
        livreExistant.setNbExemplairesTotal(nouveauLivre.getNbExemplairesTotal());
        
        Livre savedLivre = livreRepository.save(livreExistant);
        log.info("✅ Livre modifié avec succès - ID: {}, Nouveau titre: {}", savedLivre.getId(), savedLivre.getTitre());
        return savedLivre;
    }

    // Supprimer un livre
    public void supprimerLivre(Long id) {
        log.info("🗑️ Suppression du livre ID: {}", id);
        Livre livre = livreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));
        livreRepository.deleteById(id);
        log.info("✅ Livre supprimé avec succès - Titre: {}", livre.getTitre());
    }
}