import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Member {
    id?: number;
    nom: string;
    prenom: string;
    email: string;
    telephone?: string;
    dateNaissance?: string;
    adresse?: string;
    password?: string;
    role?: string;
}

@Injectable({ providedIn: 'root' })
export class AdminService {
    private apiUrl = 'http://localhost:8080/api/utilisateurs';

    constructor(private http: HttpClient) {}

    getAllMembers(): Observable<Member[]> {
        return this.http.get<Member[]>(`${this.apiUrl}/membres`);
    }

    getMemberById(id: number): Observable<Member> {
        return this.http.get<Member>(`${this.apiUrl}/${id}`);
    }

    getToutesLesStatistiques(): Observable<any> {
  return this.http.get<any>(`${this.apiUrl}/toutes`);
}
    createMember(member: Member): Observable<Member> {
        return this.http.post<Member>(`${this.apiUrl}/membres`, member);
    }

    updateMember(id: number, member: Member): Observable<Member> {
        return this.http.put<Member>(`${this.apiUrl}/membres/${id}`, member);
    }

    deleteMember(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
    // Statistiques avancées
getEmpruntsParMois(): Observable<any> {
  return this.http.get<any>(`${this.apiUrl}/statistiques/emprunts-par-mois`);
}

getTopCategories(): Observable<any> {
  return this.http.get<any>(`${this.apiUrl}/statistiques/top-categories`);
}

getMembresActifs(): Observable<any> {
  return this.http.get<any>(`${this.apiUrl}/statistiques/membres-actifs`);
}
}