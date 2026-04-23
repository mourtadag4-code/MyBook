CREATE TABLE utilisateur (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telephone VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,  -- ADMIN, BIBLIOTHECAIRE, MEMBRE
    date_inscription DATE NOT NULL,
    -- Champs spécifiques selon le rôle (peuvent être NULL)
    date_naissance DATE NULL,
    adresse VARCHAR(255) NULL,
    matricule VARCHAR(50) NULL,
    date_embauche DATE NULL,
    niveau_acces VARCHAR(50) NULL
);


CREATE TABLE livre (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titre VARCHAR(200) NOT NULL,
    auteur VARCHAR(100) NOT NULL,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    categorie VARCHAR(50),
    nb_exemplaires_total INT NOT NULL DEFAULT 1,
    nb_exemplaires_disponibles INT NOT NULL DEFAULT 1
);


CREATE TABLE emprunt (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    membre_id BIGINT NOT NULL,
    livre_id BIGINT NOT NULL,
    date_emprunt DATE NOT NULL,
    date_retour_prevue DATE NOT NULL,
    date_retour_effective DATE NULL,
    statut VARCHAR(20) NOT NULL,  -- EN_COURS, RETOURNE, EN_RETARD
    penalite DECIMAL(10,2) DEFAULT 0,
    FOREIGN KEY (membre_id) REFERENCES utilisateur(id),
    FOREIGN KEY (livre_id) REFERENCES livre(id)
);


CREATE INDEX idx_emprunt_membre ON emprunt(membre_id);
CREATE INDEX idx_emprunt_livre ON emprunt(livre_id);
CREATE INDEX idx_emprunt_statut ON emprunt(statut);
CREATE INDEX idx_livre_titre ON livre(titre);
CREATE INDEX idx_livre_auteur ON livre(auteur);
CREATE INDEX idx_utilisateur_email ON utilisateur(email);


-- Admin
INSERT INTO utilisateur (nom, prenom, email, telephone, password, role, date_inscription, niveau_acces)
VALUES ('Diop', 'Aliou', 'admin@bibliotheque.sn', '771234567', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTV6UiW', 'ADMIN', CURDATE(), 'TOUT');

-- Bibliothécaire
INSERT INTO utilisateur (nom, prenom, email, telephone, password, role, date_inscription, matricule, date_embauche)
VALUES ('Sow', 'Fatou', 'biblio@bibliotheque.sn', '772345678', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTV6UiW', 'BIBLIOTHECAIRE', CURDATE(), 'BIB-001', CURDATE());

-- Membres
INSERT INTO utilisateur (nom, prenom, email, telephone, password, role, date_inscription, date_naissance, adresse)
VALUES 
('Fall', 'Mamadou', 'mamadou.fall@email.com', '773456789', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTV6UiW', 'MEMBRE', CURDATE(), '1990-05-15', 'Dakar, Sicap Liberte'),
('Ndiaye', 'Aminata', 'aminata.ndiaye@email.com', '774567890', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTV6UiW', 'MEMBRE', CURDATE(), '1995-08-22', 'Dakar, Mermoz');

-- Livres
INSERT INTO livre (titre, auteur, isbn, categorie, nb_exemplaires_total, nb_exemplaires_disponibles)
VALUES 
('Le Petit Prince', 'Antoine de Saint-Exupéry', '9782070612758', 'Conte', 3, 3),
('1984', 'George Orwell', '9780451524935', 'Science-fiction', 2, 2),
('L''Alchimiste', 'Paulo Coelho', '9780062502175', 'Roman', 2, 2),
('Sapiens', 'Yuval Noah Harari', '9780062316097', 'Histoire', 1, 1),
('La Peste', 'Albert Camus', '9782070360420', 'Roman', 2, 2);

-- Emprunts (exemples)
INSERT INTO emprunt (membre_id, livre_id, date_emprunt, date_retour_prevue, statut)
VALUES 
(3, 1, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY), 'EN_COURS'),
(3, 2, DATE_SUB(CURDATE(), INTERVAL 10 DAY), DATE_SUB(CURDATE(), INTERVAL 4 DAY), 'EN_RETARD'),
(4, 3, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY), 'EN_COURS');