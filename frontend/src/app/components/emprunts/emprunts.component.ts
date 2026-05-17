import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
    selector: 'app-emprunts',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './emprunts.component.html',
    styleUrls: ['./emprunts.component.css']
})
export class EmpruntsComponent implements OnInit {
    empruntsEnCours: any[] = [];
    empruntsEnRetard: any[] = [];
    loading: boolean = true;

    constructor(private http: HttpClient) {}

    ngOnInit() {
        this.loadEmprunts();
    }

    loadEmprunts() {
        this.http.get<any[]>('http://localhost:8080/api/emprunts/en-cours').subscribe({
            next: async (data) => {
                for (let emprunt of data) {
                    // Charge le membre
                    if (emprunt.membre?.id) {
                        const membre = await this.http.get<any>(`http://localhost:8080/api/utilisateurs/${emprunt.membre.id}`).toPromise();
                        emprunt.membre = membre;
                    }
                    // Charge le livre
                    if (emprunt.livre?.id) {
                        const livre = await this.http.get<any>(`http://localhost:8080/api/livres/${emprunt.livre.id}`).toPromise();
                        emprunt.livre = livre;
                    }
                }
                this.empruntsEnCours = data;
                this.loading = false;
            },
            error: (err) => {
                console.error('Erreur:', err);
                this.loading = false;
            }
        });
    }
}