import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
    selector: 'app-header',
    standalone: true,
    imports: [CommonModule, RouterLink],
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
    userName: string | null = '';

    constructor(private authService: AuthService, private router: Router) {}

    ngOnInit() {
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

    logout() {
        localStorage.removeItem('token');
        this.router.navigate(['/login']);
    }
}