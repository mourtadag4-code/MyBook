import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-return-book',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './return-book.component.html',
  styleUrls: ['./return-book.component.css']
})
export class ReturnBookComponent {
  loanId: number | null = null;
  message: string = '';
  errorMessage: string = '';
  isLoading: boolean = false;

  onSubmit(): void {
    if (!this.loanId) {
      this.errorMessage = 'Veuillez saisir l\'ID de l\'emprunt';
      return;
    }

    this.isLoading = true;
    this.message = '';
    this.errorMessage = '';

    // Appel API à implémenter
    setTimeout(() => {
      this.isLoading = false;
      this.message = `Retour enregistré avec succès pour l'emprunt #${this.loanId}`;
      this.loanId = null;
    }, 1000);
  }
}