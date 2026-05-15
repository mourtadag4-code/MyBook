import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';  // ← Ajouter
import { FormsModule } from '@angular/forms';    // ← Ajouter (pour ngModel)
import { BookService, Livre } from '../../services/book.service';

@Component({
  selector: 'app-book-form',
  templateUrl: './book-form.component.html',
  styleUrls: ['./book-form.component.css'],
  imports: [CommonModule, FormsModule]  // ← Ajouter cette ligne
})
export class BookFormComponent implements OnInit {

  livre: Livre = {
    titre: '',
    auteur: '',
    isbn: '',
    categorie: '',
    nbExemplairesTotal: 1,
    nbExemplairesDisponibles: 1
  };
  isEditMode: boolean = false;
  errorMessage: string = '';

  constructor(
    private bookService: BookService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    if (id) {
      this.isEditMode = true;
      this.loadLivre(id);
    }
  }

  loadLivre(id: number): void {
    this.bookService.getLivreById(id).subscribe({
      next: (data) => {
        this.livre = data;
      },
      error: (err) => {
        console.error('Erreur:', err);
        this.errorMessage = 'Livre non trouvé';
      }
    });
  }

  onSubmit(): void {
    if (this.isEditMode) {
      this.bookService.updateLivre(this.livre.id!, this.livre).subscribe({
        next: () => {
          this.router.navigate(['/livres']);
        },
        error: (err) => {
          console.error('Erreur:', err);
          this.errorMessage = 'Erreur lors de la modification';
        }
      });
    } else {
      this.bookService.createLivre(this.livre).subscribe({
        next: () => {
          this.router.navigate(['/livres']);
        },
        error: (err) => {
          console.error('Erreur:', err);
          this.errorMessage = 'Erreur lors de l\'ajout';
        }
      });
    }
  }
}