import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { UserService, UserProfile } from '../../services/user.service';

@Component({
    selector: 'app-profile',
    standalone: true,
    imports: [ReactiveFormsModule, CommonModule],
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
    profileForm: FormGroup;
    userId: number | null = null;
    successMessage = '';
    errorMessage = '';

    constructor(
        private fb: FormBuilder,
        private authService: AuthService,
        private userService: UserService
    ) {
        this.profileForm = this.fb.group({
            nom: ['', [Validators.required, Validators.minLength(2)]],
            prenom: ['', [Validators.required, Validators.minLength(2)]],
            email: ['', [Validators.required, Validators.email]],
            telephone: ['', [Validators.pattern('^[0-9]{9}$')]],
            dateNaissance: [''],
            adresse: ['']
        });
    }

    ngOnInit(): void {
        const token = this.authService.getToken();
        if (token) {
            try {
                const payload = JSON.parse(atob(token.split('.')[1]));
                this.userId = payload.id;
                this.loadProfile();
            } catch (e) {
                console.error('Erreur token', e);
            }
        }
    }

    loadProfile(): void {
        if (this.userId) {
            this.userService.getProfile(this.userId).subscribe({
                next: (data) => {
                    this.profileForm.patchValue(data);
                },
                error: (err) => {
                    this.errorMessage = 'Erreur lors du chargement du profil';
                    console.error(err);
                }
            });
        }
    }

    // Messages d'erreur personnalisés
    getErrorMessage(controlName: string): string {
        const control = this.profileForm.get(controlName);
        
        if (control?.hasError('required')) {
            return 'Ce champ est obligatoire';
        }
        if (control?.hasError('email')) {
            return 'Veuillez entrer un email valide';
        }
        if (control?.hasError('minlength')) {
            const requiredLength = control.errors?.['minlength'].requiredLength;
            return `Minimum ${requiredLength} caractères`;
        }
        if (control?.hasError('pattern')) {
            return 'Le numéro de téléphone doit contenir 9 chiffres';
        }
        return '';
    }

    onSubmit(): void {
        if (this.profileForm.valid && this.userId) {
            this.userService.updateProfile(this.userId, this.profileForm.value).subscribe({
                next: () => {
                    this.successMessage = 'Profil mis à jour avec succès !';
                    setTimeout(() => this.successMessage = '', 3000);
                },
                error: (err) => {
                    this.errorMessage = 'Erreur lors de la mise à jour';
                    console.error(err);
                }
            });
        }
    }
}