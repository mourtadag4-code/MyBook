import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
    const router = inject(Router);
    const toastr = inject(ToastrService);

    return next(req).pipe(
        catchError((error) => {
            let message = 'Une erreur est survenue';
            
            switch (error.status) {
                case 400:
                    message = 'Données invalides. Vérifiez votre saisie.';
                    break;
                case 401:
                    message = 'Non authentifié. Redirection vers la connexion...';
                    router.navigate(['/login']);
                    break;
                case 403:
                    message = 'Accès interdit. Vous n\'avez pas les droits nécessaires.';
                    break;
                case 404:
                    message = 'Ressource non trouvée.';
                    break;
                case 409:
                    message = 'Conflit : cette ressource existe déjà (email, ISBN...).';
                    break;
                case 500:
                    message = 'Erreur serveur. Veuillez réessayer plus tard.';
                    break;
                default:
                    message = error.error?.message || message;
            }
            
            toastr.error(message, 'Erreur');
            console.error('Erreur HTTP:', error);
            
            return throwError(() => error);
        })
    );
};