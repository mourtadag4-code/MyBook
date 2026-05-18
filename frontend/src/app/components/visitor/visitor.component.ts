import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookService, Livre } from '../../services/book.service';

@Component({
  selector: 'app-visitor',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './visitor.component.html',
  styleUrls: ['./visitor.component.css']
})
export class VisitorComponent implements OnInit {
  livres: Livre[] = [];
  loading: boolean = true;
  errorMessage: string = '';

  constructor(private bookService: BookService) {}

  ngOnInit(): void {
    this.loadLivres();
  }

  loadLivres(): void {
    this.bookService.getAllLivres().subscribe({
      next: (data) => {
        this.livres = data;
        this.loading = false;
      },
      error: (err) => {
        this.errorMessage = 'Erreur chargement des livres';
        this.loading = false;
      }
    });
  }
}