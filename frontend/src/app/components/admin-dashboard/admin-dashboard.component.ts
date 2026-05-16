import { Component, OnInit, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminService } from '../../services/admin.service';
import Chart from 'chart.js/auto';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit, AfterViewInit {
  stats: any = {
    totalLivres: 0,
    totalMembres: 0,
    empruntsEnCours: 0,
    empruntsEnRetard: 0,
    livresPlusEmpruntes: []
  };
  
  loading: boolean = true;
  errorMessage: string = '';
  
  empruntsParMois: any[] = [];
  topCategories: any[] = [];

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.loadStatistiques();
    this.loadEmpruntsParMois();
    this.loadTopCategories();
  }

  ngAfterViewInit(): void {
    setTimeout(() => this.initCharts(), 500);
  }

  loadStatistiques(): void {
    this.adminService.getToutesLesStatistiques().subscribe({
      next: (data) => {
        this.stats = data;
        this.loading = false;
      },
      error: (err) => {
        this.errorMessage = 'Erreur chargement stats';
        this.loading = false;
      }
    });
  }

  loadEmpruntsParMois(): void {
    this.adminService.getEmpruntsParMois().subscribe({
      next: (data) => {
        this.empruntsParMois = data;
        this.initCharts();
      },
      error: (err) => console.error(err)
    });
  }

  loadTopCategories(): void {
    this.adminService.getTopCategories().subscribe({
      next: (data) => {
        this.topCategories = data;
        this.initCharts();
      },
      error: (err) => console.error(err)
    });
  }

  initCharts(): void {
    // Graphique emprunts par mois
    if (this.empruntsParMois.length > 0) {
      const ctx = document.getElementById('monthlyChart') as HTMLCanvasElement;
      if (ctx) {
        new Chart(ctx, {
          type: 'bar',
          data: {
            labels: this.empruntsParMois.map(item => item.mois),
            datasets: [{
              label: 'Emprunts',
              data: this.empruntsParMois.map(item => item.nbEmprunts),
              backgroundColor: '#007bff',
              borderColor: '#0056b3',
              borderWidth: 1
            }]
          },
          options: {
            responsive: true,
            plugins: {
              legend: { position: 'top' },
              title: { display: true, text: 'Emprunts par mois' }
            }
          }
        });
      }
    }

    // Graphique catégories
    if (this.topCategories.length > 0) {
      const ctx2 = document.getElementById('categoryChart') as HTMLCanvasElement;
      if (ctx2) {
        new Chart(ctx2, {
          type: 'pie',
          data: {
            labels: this.topCategories.map(item => item.categorie),
            datasets: [{
              data: this.topCategories.map(item => item.nbEmprunts),
              backgroundColor: ['#007bff', '#28a745', '#ffc107', '#dc3545', '#17a2b8']
            }]
          },
          options: {
            responsive: true,
            plugins: {
              legend: { position: 'top' },
              title: { display: true, text: 'Top catégories' }
            }
          }
        });
      }
    }
  }

  get livresTop(): any[] {
    return this.stats.livresPlusEmpruntes || [];
  }
}