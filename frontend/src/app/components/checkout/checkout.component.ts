import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent {
  memberId: number | null = null;
  bookId: number | null = null;
  message: string = '';
  errorMessage: string = '';
  isLoading: boolean = false;

  constructor(private router: Router) {}

  onSubmit(): void {
    if (!this.memberId || !this.bookId) {
      this.errorMessage = 'Veuillez saisir l\'ID du membre et l\'ISBN du livre';
      return;
    }

    this.isLoading = true;
    this.message = '';
    this.errorMessage = '';

    // Appel API à implémenter
    setTimeout(() => {
      this.isLoading = false;
      this.message = `Emprunt enregistré avec succès ! Livre #${this.bookId} emprunté par membre #${this.memberId}`;
      this.memberId = null;
      this.bookId = null;
    }, 1000);
  }
}