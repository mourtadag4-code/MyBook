import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardService } from '../../services/dashboard';


@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-dashboard.component.html'
})
export class AdminDashboardComponent implements OnInit {

  // ✅ AJOUT IMPORTANT
  loading = true;
  errorMessage = '';

  stats = {
    totalLivres: 0,
    totalMembres: 0,
    empruntsEnCours: 0,
    empruntsEnRetard: 0
  };

  livresTop: any[] = [];
  overdueList: any[] = [];

  constructor(private dashboardService: DashboardService) {}

  ngOnInit() {
    this.loadDashboard();
  }

  loadDashboard() {

    this.loading = true;

    this.dashboardService.getTotalBooks().subscribe({
      next: (data) => this.stats.totalLivres = data,
      error: () => this.errorMessage = "Erreur chargement livres"
    });

    this.dashboardService.getTotalMembers().subscribe({
      next: (data) => this.stats.totalMembres = data,
      error: () => this.errorMessage = "Erreur chargement membres"
    });

    this.dashboardService.getCurrentLoans().subscribe({
      next: (data) => this.stats.empruntsEnCours = data.length,
      error: () => this.errorMessage = "Erreur emprunts"
    });

    this.dashboardService.getOverdueLoans().subscribe({
      next: (data) => {
        this.overdueList = data;
        this.stats.empruntsEnRetard = data.length;
      },
      error: () => this.errorMessage = "Erreur retards"
    });

    this.dashboardService.getTopBooks().subscribe({
      next: (data) => {
        this.livresTop = data;
        this.loading = false; // ✅ FIN chargement
      },
      error: () => {
        this.errorMessage = "Erreur top livres";
        this.loading = false;
      }
    });
  }
}