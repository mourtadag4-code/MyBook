package com.bookmy.backend.dto;

public class LivreDTO {
    private Long id;
    private String titre;
    private String auteur;
    private String isbn;
    private String categorie;
    private Integer nbExemplairesTotal;
    private Integer nbExemplairesDisponibles;

    // Constructeur par défaut
    public LivreDTO() {}

    // Constructeur avec tous les champs
    public LivreDTO(Long id, String titre, String auteur, String isbn, 
                    String categorie, Integer nbExemplairesTotal, Integer nbExemplairesDisponibles) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
        this.categorie = categorie;
        this.nbExemplairesTotal = nbExemplairesTotal;
        this.nbExemplairesDisponibles = nbExemplairesDisponibles;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public Integer getNbExemplairesTotal() { return nbExemplairesTotal; }
    public void setNbExemplairesTotal(Integer nbExemplairesTotal) { this.nbExemplairesTotal = nbExemplairesTotal; }

    public Integer getNbExemplairesDisponibles() { return nbExemplairesDisponibles; }
    public void setNbExemplairesDisponibles(Integer nbExemplairesDisponibles) { 
        this.nbExemplairesDisponibles = nbExemplairesDisponibles; 
    }
}