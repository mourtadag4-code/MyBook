import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { AuthService } from './services/auth.service';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [CommonModule, RouterModule],
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {
    userName: string | null = '';

    constructor(private authService: AuthService, private router: Router) {
        this.loadUserInfo();
    }

    loadUserInfo() {
        const token = this.authService.getToken();
        if (token) {
            try {
                const payload = JSON.parse(atob(token.split('.')[1]));
                this.userName = payload.nom || payload.email;
            } catch (e) {
                console.error('Erreur token', e);
            }
        }
    }

    hasRole(role: string): boolean {
        return this.authService.getRoleFromToken() === role;
    }

    logout(): void {
        localStorage.removeItem('token');
        this.router.navigate(['/login']);
    }
}