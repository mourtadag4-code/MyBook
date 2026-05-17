import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
    private apiUrl = 'http://localhost:8080/api/auth';

    constructor(private http: HttpClient) {}

    register(userData: any): Observable<any> {
        return this.http.post(`${this.apiUrl}/register`, userData);
    }

    login(credentials: any): Observable<any> {
        return this.http.post(`${this.apiUrl}/login`, credentials);
    }

    saveToken(token: string): void {
        localStorage.setItem('token', token);
    }

    getToken(): string | null {
        return localStorage.getItem('token');
    }

    isLoggedIn(): boolean {
        return !!this.getToken();
    }

    getRoleFromToken(): string | null {
        const token = this.getToken();
        if (!token) return null;
        try {
            const payload = JSON.parse(atob(token.split('.')[1]));
            const role = payload.role;
            if (role && role.startsWith('ROLE_')) {
                return role.substring(5);
            }
            return role;
        } catch {
            return null;
        }
    }

    getUserId(): number | null {
        const token = this.getToken();
        if (!token) return null;
        try {
            const payload = JSON.parse(atob(token.split('.')[1]));
            console.log('🔍 PAYLOAD:', payload);
            console.log('🔍 ID trouvé:', payload.id);
            return payload.id;
        } catch {
            return null;
        }
    }
}