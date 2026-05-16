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
            
            // LOGS POUR DEBUG
            console.log("Réponse complète:", res);
            console.log("Role reçu:", res.role);
            console.log("Token reçu:", res.token);
            
            const role = res.role;
            
            if (role === 'ADMIN') {
                console.log("Redirection vers /admin");
                this.router.navigate(['/admin']);
            } else if (role === 'BIBLIOTHECAIRE') {
                console.log("Redirection vers /bibliothecaire");
                this.router.navigate(['/bibliothecaire']);
            } else if (role === 'MEMBRE') {
                console.log("Redirection vers /membre");
                this.router.navigate(['/membre']);
            } else {
                console.log("Rôle non reconnu:", role);
                alert("Rôle inconnu: " + role);
            }
        },
        error: () => {
            alert('Email ou mot de passe incorrect');
        }
    });

}
}