import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Emprunt {
  id: number;
  livreTitre: string;
  livreAuteur: string;
  dateEmprunt: string;
  dateRetourPrevue: string;
  dateRetourEffective?: string;
  statut: string;
  penalite: number;
}

@Injectable({
  providedIn: 'root'
})
export class LoanService {
  private apiUrl = 'http://localhost:8080/api/emprunts';

  constructor(private http: HttpClient) {}

  getLoanHistory(): Observable<Emprunt[]> {
    return this.http.get<Emprunt[]>(`${this.apiUrl}/historique`);
  }

  getCurrentLoans(): Observable<Emprunt[]> {
    return this.http.get<Emprunt[]>(`${this.apiUrl}/en-cours`);
  }

  getPastLoans(): Observable<Emprunt[]> {
    return this.http.get<Emprunt[]>(`${this.apiUrl}/passes`);
  }
}