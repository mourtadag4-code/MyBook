import { Component, OnInit } from '@angular/core';
import { AdminService, Statistiques, LivreStat } from '../../services/admin.service';
import { CommonModule } from '@angular/common';  

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css'],
  imports: [CommonModule]  // ← Ajouter cette ligne
})
export class AdminDashboardComponent implements OnInit {

  stats: Statistiques = {
    totalLivres: 0,
    totalMembres: 0,
    empruntsEnCours: 0,
    empruntsEnRetard: 0,
    livresPlusEmpruntes: []
  };

  loading: boolean = true;
  errorMessage: string = '';

  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
    this.loadStatistiques();
  }

  loadStatistiques(): void {
    this.loading = true;
    this.adminService.getToutesLesStatistiques().subscribe({
      next: (data) => {
        this.stats = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur:', err);
        this.errorMessage = 'Impossible de charger les statistiques. Vérifiez que le backend est démarré.';
        this.loading = false;
      }
    });
  }

  get livresTop(): LivreStat[] {
    return this.stats.livresPlusEmpruntes || [];
  }
}