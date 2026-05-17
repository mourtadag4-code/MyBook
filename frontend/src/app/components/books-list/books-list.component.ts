import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BookService, Livre } from '../../services/book.service';
import { AuthService } from '../../services/auth.service';
import { timeout } from 'rxjs/operators';

@Component({
    selector: 'app-books-list',
    templateUrl: './books-list.component.html',
    styleUrls: ['./books-list.component.css'],
    imports: [CommonModule, RouterModule, FormsModule],
    standalone: true
})
export class BooksListComponent implements OnInit {
    livres: Livre[] = [];
    filteredLivres: Livre[] = [];
    searchTerm: string = '';
    loading: boolean = true;
    errorMessage: string = '';
    livreASupprimer: Livre | null = null;
    
    // Variables pour le modal de modification
    showEditModal: boolean = false;
    livreEnModification: any = {
        id: null,
        titre: '',
        auteur: '',
        isbn: '',
        categorie: '',
        nbExemplairesTotal: 0,
        nbExemplairesDisponibles: 0
    };

    constructor(
        private bookService: BookService,
        private authService: AuthService,
        private http: HttpClient
    ) {}

    ngOnInit(): void {
        this.loadLivres();
    }

    loadLivres(): void {
        this.loading = true;
        this.bookService.getAllLivres().pipe(
            timeout(15000)
        ).subscribe({
            next: (data) => {
                this.livres = data;
                this.filteredLivres = [...data];
                this.loading = false;
            },
            error: (err) => {
                console.error('Erreur:', err);
                this.errorMessage = 'Impossible de charger les livres';
                this.loading = false;
            }
        });
    }

    // Filtrer les livres par recherche
    filterLivres(): void {
        if (!this.searchTerm || this.searchTerm.trim() === '') {
            this.filteredLivres = [...this.livres];
        } else {
            const term = this.searchTerm.toLowerCase();
            this.filteredLivres = this.livres.filter(livre => 
                livre.titre.toLowerCase().includes(term) ||
                livre.auteur.toLowerCase().includes(term) ||
                livre.categorie.toLowerCase().includes(term)
            );
        }
    }

    hasRole(role: string): boolean {
        const userRole = this.authService.getRoleFromToken();
        if (!userRole) return false;
        return userRole === role;
    }

    emprunterLivre(livreId: number): void {
        const membreId = this.authService.getUserId();
        if (!membreId) {
            alert('Vous devez être connecté');
            return;
        }

        console.log('Emprunt - membreId:', membreId, 'livreId:', livreId);

        this.http.post(`http://localhost:8080/api/emprunts/emprunter?membreId=${membreId}&livreId=${livreId}`, {})
            .subscribe({
                next: () => {
                    alert('Livre emprunté avec succès !');
                    this.loadLivres();
                },
                error: (err) => {
                    console.error('Erreur emprunt:', err);
                    alert('Erreur: ' + (err.error?.message || err.message));
                }
            });
    }

    // ========== MÉTHODES POUR MODIFIER ==========
    
    modifierLivre(livre: Livre): void {
        console.log('📝 Modification du livre:', livre);
        
        this.livreEnModification = {
            id: livre.id,
            titre: livre.titre,
            auteur: livre.auteur,
            isbn: livre.isbn,
            categorie: livre.categorie,
            nbExemplairesTotal: livre.nbExemplairesTotal,
            nbExemplairesDisponibles: livre.nbExemplairesDisponibles
        };
        this.showEditModal = true;
    }

    enregistrerModification(): void {
        if (!this.livreEnModification.id) return;
        
        this.http.put(`http://localhost:8080/api/livres/${this.livreEnModification.id}`, this.livreEnModification)
            .subscribe({
                next: () => {
                    alert('Livre modifié avec succès !');
                    this.closeEditModal();
                    this.loadLivres();
                },
                error: (err) => {
                    console.error('Erreur modification:', err);
                    alert('Erreur lors de la modification: ' + (err.error?.message || err.message));
                }
            });
    }

    closeEditModal(): void {
        this.showEditModal = false;
        this.livreEnModification = {
            id: null,
            titre: '',
            auteur: '',
            isbn: '',
            categorie: '',
            nbExemplairesTotal: 0,
            nbExemplairesDisponibles: 0
        };
    }

    confirmDelete(livre: Livre): void {
        this.livreASupprimer = livre;
    }

    deleteLivre(): void {
        if (this.livreASupprimer && this.livreASupprimer.id) {
            this.bookService.deleteLivre(this.livreASupprimer.id).subscribe({
                next: () => {
                    this.loadLivres();
                    this.livreASupprimer = null;
                },
                error: (err) => {
                    console.error('Erreur suppression:', err);
                    this.errorMessage = 'Impossible de supprimer le livre';
                }
            });
        }
    }

    cancelDelete(): void {
        this.livreASupprimer = null;
    }
}