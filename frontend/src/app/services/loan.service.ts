import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

export interface Emprunt {
    id: number;
    livre?: { titre: string; auteur: string };
    dateEmprunt: string;
    dateRetourPrevue: string;
    dateRetourEffective?: string;
    statut: string;
    penalite: number;
}

@Injectable({ providedIn: 'root' })
export class LoanService {
    private apiUrl = 'http://localhost:8080/api/emprunts';

    constructor(private http: HttpClient, private authService: AuthService) {}

    getLoanHistory(): Observable<Emprunt[]> {
        const membreId = this.authService.getUserId();
        if (!membreId) {
            throw new Error('Non authentifié');
        }
        return this.http.get<Emprunt[]>(`${this.apiUrl}/membre/${membreId}`);
    }

    getCurrentLoans(): Observable<Emprunt[]> {
        return this.http.get<Emprunt[]>(`${this.apiUrl}/en-cours`);
    }

    getOverdueLoans(): Observable<Emprunt[]> {
        return this.http.get<Emprunt[]>(`${this.apiUrl}/en-retard`);
    }
}