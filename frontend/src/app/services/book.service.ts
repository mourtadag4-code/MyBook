import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Livre {
  id?: number;
  titre: string;
  auteur: string;
  isbn: string;
  categorie: string;
  nbExemplairesTotal: number;
  nbExemplairesDisponibles: number;
}

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private apiUrl = 'http://localhost:8080/api/livres';

  constructor(private http: HttpClient) { }

  // Récupérer tous les livres
  getAllLivres(): Observable<Livre[]> {
    return this.http.get<Livre[]>(this.apiUrl);
  }

  // Récupérer un livre par son ID
  getLivreById(id: number): Observable<Livre> {
    return this.http.get<Livre>(`${this.apiUrl}/${id}`);
  }

  // Ajouter un livre
  createLivre(livre: Livre): Observable<Livre> {
    return this.http.post<Livre>(this.apiUrl, livre);
  }

  // Modifier un livre
  updateLivre(id: number, livre: Livre): Observable<Livre> {
    return this.http.put<Livre>(`${this.apiUrl}/${id}`, livre);
  }

  // Supprimer un livre
  deleteLivre(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  searchBooks(keyword: string, category: string): Observable<Livre[]> {
  let params = '';
  if (keyword) params += `&keyword=${keyword}`;
  if (category) params += `&category=${category}`;
  return this.http.get<Livre[]>(`${this.apiUrl}/recherche?${params}`);
}
}