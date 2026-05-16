import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EmpruntService {

  apiUrl = 'http://localhost:3000/emprunts';

  constructor(private http: HttpClient) {}

  getEmprunts() {
    return this.http.get(this.apiUrl);
  }

  ajouterEmprunt(data: any) {
    return this.http.post(this.apiUrl, data);
  }

  retournerLivre(id: number) {
    return this.http.put(`${this.apiUrl}/${id}/retour`, {});
  }
}