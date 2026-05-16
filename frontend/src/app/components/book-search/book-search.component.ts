import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BookService, Livre } from '../../services/book.service';

@Component({
  selector: 'app-book-search',
  templateUrl: './book-search.component.html',
  styleUrls: ['./book-search.component.css'],
  imports: [CommonModule, FormsModule]
})
export class BookSearchComponent {
  searchKeyword: string = '';
  selectedCategory: string = '';
  categories: string[] = ['Tous', 'Roman', 'Science-fiction', 'Conte', 'Histoire', 'Biographie'];
  livres: Livre[] = [];
  loading: boolean = false;
  errorMessage: string = '';

  constructor(private bookService: BookService) {}

  searchBooks(): void {
    if (!this.searchKeyword.trim() && this.selectedCategory === 'Tous') {
      this.loadAllBooks();
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    let category = this.selectedCategory === 'Tous' ? '' : this.selectedCategory;
    
    this.bookService.searchBooks(this.searchKeyword, category).subscribe({
      next: (data) => {
        this.livres = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur:', err);
        this.errorMessage = 'Erreur lors de la recherche';
        this.loading = false;
      }
    });
  }

  loadAllBooks(): void {
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

  clearSearch(): void {
    this.searchKeyword = '';
    this.selectedCategory = 'Tous';
    this.loadAllBooks();
  }

  ngOnInit(): void {
    this.loadAllBooks();
  }
}