package com.bookmy.backend;

import com.bookmy.backend.model.Livre;
import com.bookmy.backend.model.Utilisateur;
import com.bookmy.backend.model.Role;

public class TestDataFactory {

    public static Livre createTestLivre() {
        Livre livre = new Livre();
        livre.setId(1L);
        livre.setTitre("Le Petit Prince");
        livre.setAuteur("Saint-Exupéry");
        livre.setIsbn("9782070612758");
        livre.setCategorie("Conte");
        livre.setNbExemplairesTotal(3);
        livre.setNbExemplairesDisponibles(3);
        return livre;
    }

    public static Utilisateur createTestUtilisateur() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setEmail("test@test.com");
        utilisateur.setNom("Dupont");
        utilisateur.setPrenom("Jean");
        utilisateur.setRole(Role.MEMBRE);
        return utilisateur;
    }
}