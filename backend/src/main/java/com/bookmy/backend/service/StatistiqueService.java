package com.bookmy.backend.service;

import com.bookmy.backend.model.Emprunt;
import com.bookmy.backend.model.Livre;
import com.bookmy.backend.model.StatutEmprunt;
import com.bookmy.backend.model.Utilisateur;
import com.bookmy.backend.repository.EmpruntRepository;
import com.bookmy.backend.repository.LivreRepository;
import com.bookmy.backend.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatistiqueService {

    private final LivreRepository livreRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final EmpruntRepository empruntRepository;

    // Constructeur pour l'injection des dépendances
    public StatistiqueService(LivreRepository livreRepository,
                              UtilisateurRepository utilisateurRepository,
                              EmpruntRepository empruntRepository) {
        this.livreRepository = livreRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.empruntRepository = empruntRepository;
    }

    /**
     * Nombre total de livres dans la bibliothèque
     */
    public long getTotalLivres() {
        return livreRepository.count();
    }

    /**
     * Nombre total de membres inscrits
     */
    public long getTotalMembres() {
        return utilisateurRepository.count();
    }

    /**
     * Nombre d'emprunts en cours
     */
    public long getEmpruntsEnCours() {
        return empruntRepository.countByStatut(StatutEmprunt.EN_COURS);
    }

    /**
     * Nombre d'emprunts en retard
     */
    public long getEmpruntsEnRetard() {
        return empruntRepository.countByStatut(StatutEmprunt.EN_RETARD);
    }

    /**
     * Livres les plus empruntés (top 5)
     */
    public List<Map<String, Object>> getLivresPlusEmpruntes() {
        // Récupérer tous les emprunts
        List<Emprunt> emprunts = empruntRepository.findAll();
        
        // Compter les emprunts par livre
        Map<Livre, Long> compteur = emprunts.stream()
                .filter(e -> e.getStatut() == StatutEmprunt.RETOURNE || e.getStatut() == StatutEmprunt.EN_COURS)
                .collect(Collectors.groupingBy(Emprunt::getLivre, Collectors.counting()));
        
        // Trier par nombre d'emprunts décroissant et prendre les 5 premiers
        return compteur.entrySet().stream()
                .sorted(Map.Entry.<Livre, Long>comparingByValue().reversed())
                .limit(5)
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("id", entry.getKey().getId());
                    result.put("titre", entry.getKey().getTitre());
                    result.put("auteur", entry.getKey().getAuteur());
                    result.put("nbEmprunts", entry.getValue());
                    return result;
                })
                .collect(Collectors.toList());
    }

    /**
     * Toutes les statistiques en une seule méthode
     */
    public Map<String, Object> getToutesLesStatistiques() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalLivres", getTotalLivres());
        stats.put("totalMembres", getTotalMembres());
        stats.put("empruntsEnCours", getEmpruntsEnCours());
        stats.put("empruntsEnRetard", getEmpruntsEnRetard());
        stats.put("livresPlusEmpruntes", getLivresPlusEmpruntes());
        return stats;
    }
}