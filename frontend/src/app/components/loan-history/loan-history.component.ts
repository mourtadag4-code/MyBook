import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoanService, Emprunt } from '../../services/loan.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-loan-history',
  templateUrl: './loan-history.component.html',
  styleUrls: ['./loan-history.component.css'],
  imports: [CommonModule]
})
export class LoanHistoryComponent implements OnInit {
  currentLoans: Emprunt[] = [];
  pastLoans: Emprunt[] = [];
  loading: boolean = true;
  errorMessage: string = '';
  activeTab: 'current' | 'past' = 'current';

  constructor(
    private loanService: LoanService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadLoans();
  }

  loadLoans(): void {
    this.loading = true;
    this.loanService.getLoanHistory().subscribe({
      next: (data) => {
        this.currentLoans = data.filter(l => l.statut === 'EN_COURS' || l.statut === 'EN_RETARD');
        this.pastLoans = data.filter(l => l.statut === 'RETOURNE');
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur:', err);
        this.errorMessage = 'Impossible de charger votre historique d\'emprunts.';
        this.loading = false;
      }
    });
  }

  getPenaliteClass(penalite: number): string {
    if (penalite > 0) return 'penalite-positive';
    return '';
  }

  isLate(returnDate: string): boolean {
    return new Date(returnDate) < new Date();
  }
}