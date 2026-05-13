package com.bookmy.backend.repository;

import com.bookmy.backend.model.Emprunt;
import com.bookmy.backend.model.StatutEmprunt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {
    
    // Trouver tous les emprunts d'un membre
    List<Emprunt> findByMembreId(Long membreId);
    
    // Trouver tous les emprunts d'un livre
    List<Emprunt> findByLivreId(Long livreId);
    
    // Trouver les emprunts par statut (EN_COURS, RETOURNE, EN_RETARD)
    List<Emprunt> findByStatut(StatutEmprunt statut);
    
    // Trouver les emprunts en cours d'un membre
    List<Emprunt> findByMembreIdAndStatut(Long membreId, StatutEmprunt statut);
    
    // Compter les emprunts par statut
    long countByStatut(StatutEmprunt statut);
}