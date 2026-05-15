import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Statistiques {
  totalLivres: number;
  totalMembres: number;
  empruntsEnCours: number;
  empruntsEnRetard: number;
  livresPlusEmpruntes: LivreStat[];
}

export interface LivreStat {
  id: number;
  titre: string;
  auteur: string;
  nbEmprunts: number;
}

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private apiUrl = 'http://localhost:8080/api/statistiques';

  constructor(private http: HttpClient) { }

  // Récupérer toutes les statistiques en une seule requête
  getToutesLesStatistiques(): Observable<Statistiques> {
    return this.http.get<Statistiques>(`${this.apiUrl}/toutes`);
  }
}