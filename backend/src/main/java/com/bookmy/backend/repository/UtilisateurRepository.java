package com.bookmy.backend.repository;

import com.bookmy.backend.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    
    // Rechercher un utilisateur par son email
    Optional<Utilisateur> findByEmail(String email);
    
    // CETTE MÉTHODE (optionnelle mais utile)
    List<Utilisateur> findByRole(String role);
}