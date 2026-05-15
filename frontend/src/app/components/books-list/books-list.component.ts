import { Component, OnInit } from '@angular/core';
import { BookService, Livre } from '../../services/book.service';
import { CommonModule } from '@angular/common';  // ← Ajouter
import { RouterModule } from '@angular/router';   // ← Ajouter (pour routerLink)

@Component({
  selector: 'app-books-list',
  templateUrl: './books-list.component.html',
  styleUrls: ['./books-list.component.css'],
  imports: [CommonModule, RouterModule]  // ← Ajouter cette ligne
})
export class BooksListComponent implements OnInit {

  livres: Livre[] = [];
  loading: boolean = true;
  errorMessage: string = '';
  livreASupprimer: Livre | null = null;

  constructor(private bookService: BookService) { }

  ngOnInit(): void {
    this.loadLivres();
  }

  loadLivres(): void {
    this.loading = true;
    this.bookService.getAllLivres().subscribe({
      next: (data) => {
        this.livres = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur:', err);
        this.errorMessage = 'Impossible de charger les livres';
        this.loading = false;
      }
    });
  }

  confirmDelete(livre: Livre): void {
    this.livreASupprimer = livre;
  }

  deleteLivre(): void {
    if (this.livreASupprimer) {
      this.bookService.deleteLivre(this.livreASupprimer.id!).subscribe({
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