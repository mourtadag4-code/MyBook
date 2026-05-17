import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
    selector: 'app-admin-dashboard',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './admin-dashboard.component.html',
    styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
    stats = {
        totalLivres: 0,
        totalMembres: 0,
        empruntsEnCours: 0,
        empruntsEnRetard: 0
    };
    topLivres: any[] = [];
    loading = true;
    errorMessage = '';

    constructor(private http: HttpClient) {}

    ngOnInit() {
        this.loadStatistiques();
        this.loadTopLivres();
    }

    loadStatistiques() {
        let compteur = 0;
        const totalAppels = 4;
        const verifierChargement = () => {
            compteur++;
            if (compteur === totalAppels) {
                if (this.topLivres !== undefined) {
                    this.loading = false;
                }
            }
        };

        // 1. Charger les livres
        this.http.get<any[]>('http://localhost:8080/api/livres').subscribe({
            next: (data) => {
                this.stats.totalLivres = data.length;
                verifierChargement();
            },
            error: (err) => {
                console.error('Erreur livres:', err);
                this.stats.totalLivres = 0;
                verifierChargement();
            }
        });

        // 2. Charger les membres
        this.http.get<any[]>('http://localhost:8080/api/utilisateurs/membres').subscribe({
            next: (data) => {
                this.stats.totalMembres = data.length;
                verifierChargement();
            },
            error: (err) => {
                console.error('Erreur membres:', err);
                this.stats.totalMembres = 0;
                verifierChargement();
            }
        });

        // 3. Charger les emprunts en cours
        this.http.get<any[]>('http://localhost:8080/api/emprunts/en-cours').subscribe({
            next: (data) => {
                this.stats.empruntsEnCours = data.filter(e => e.statut === 'EN_COURS').length;
                verifierChargement();
            },
            error: (err) => {
                console.error('Erreur emprunts en cours:', err);
                this.stats.empruntsEnCours = 0;
                verifierChargement();
            }
        });

        // 4. Charger les emprunts en retard
        this.http.get<any[]>('http://localhost:8080/api/emprunts/en-retard').subscribe({
            next: (data) => {
                this.stats.empruntsEnRetard = data.length;
                verifierChargement();
            },
            error: (err) => {
                console.error('Erreur emprunts en retard:', err);
                this.stats.empruntsEnRetard = 0;
                verifierChargement();
            }
        });
    }

    loadTopLivres() {
        this.http.get<any[]>('http://localhost:8080/api/emprunts').subscribe({
            next: (emprunts) => {
                const livreCount = new Map<number, { titre: string, auteur: string, count: number }>();
                
                emprunts.forEach(emprunt => {
                    const livreId = emprunt.livre?.id || emprunt.livreId;
                    const titre = emprunt.livre?.titre || 'Livre inconnu';
                    const auteur = emprunt.livre?.auteur || 'Auteur inconnu';
                    
                    if (livreId) {
                        if (!livreCount.has(livreId)) {
                            livreCount.set(livreId, { titre, auteur, count: 0 });
                        }
                        livreCount.get(livreId)!.count++;
                    }
                });
                
                this.topLivres = Array.from(livreCount.values())
                    .sort((a, b) => b.count - a.count)
                    .slice(0, 5);
                
                if (this.stats.totalLivres > 0) {
                    this.loading = false;
                }
            },
            error: (err) => {
                console.error('Erreur top livres:', err);
                this.topLivres = [];
                if (this.stats.totalLivres > 0) {
                    this.loading = false;
                }
            }
        });
    }
}