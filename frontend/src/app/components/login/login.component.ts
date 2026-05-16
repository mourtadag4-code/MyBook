import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [ReactiveFormsModule, CommonModule, RouterLink],
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent {

    loginForm: FormGroup;

    constructor(
        private fb: FormBuilder,
        private authService: AuthService,
        private router: Router
    ) {
        this.loginForm = this.fb.group({
            email: ['', [Validators.required, Validators.email]],
            password: ['', Validators.required]
        });
    }

    onSubmit(): void {
        if (!this.loginForm.valid) return;

        this.authService.login(this.loginForm.value).subscribe({
            next: (res) => {

                this.authService.saveToken(res.token);

                alert('Connexion réussie !');

                const role = this.authService.getRoleFromToken();
                console.log("ROLE =", role);

                // 🎯 ADAPTÉ À TA BASE DE DONNÉES
                if (role === 'ADMIN') {
                    this.router.navigate(['/admin']);

                } else if (role === 'BIBLIOTHECAIRE') {
                    this.router.navigate(['/bibliothecaire']);

                } else if (role === 'MEMBRE') {
                    this.router.navigate(['/member']);

                } else {
                    alert("Rôle inconnu ou token invalide");
                }
            },
            error: () => {
                alert('Email ou mot de passe incorrect');
            }
        });
    }
}