import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface UserProfile {
    id?: number;
    nom: string;
    prenom: string;
    email: string;
    telephone?: string;
    dateNaissance?: string;
    adresse?: string;
}

@Injectable({ providedIn: 'root' })
export class UserService {
    private apiUrl = 'http://localhost:8080/api/utilisateurs';

    constructor(private http: HttpClient) {}

    getProfile(id: number): Observable<UserProfile> {
        return this.http.get<UserProfile>(`${this.apiUrl}/${id}`);
    }

    updateProfile(id: number, profile: UserProfile): Observable<UserProfile> {
        return this.http.put<UserProfile>(`${this.apiUrl}/membres/${id}`, profile);
    }
}