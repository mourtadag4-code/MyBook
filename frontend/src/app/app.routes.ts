import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { BooksListComponent } from './components/books-list/books-list.component';
import { BookFormComponent } from './components/book-form/book-form.component';
import { BookSearchComponent } from './components/book-search/book-search.component';
import { LoanHistoryComponent } from './components/loan-history/loan-history.component';
import { RoleGuard } from './guards/role.guard';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { ReturnBookComponent } from './components/return-book/return-book.component';
import { BibliothecaireDashboardComponent } from './components/bibliothecaire-dashboard/bibliothecaire-dashboard.component';


export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'recherche', component: BookSearchComponent },
  { path: 'historique', component: LoanHistoryComponent, canActivate: [RoleGuard], data: { roles: ['MEMBRE'] } },
  
  // Routes ADMIN
  { 
    path: 'admin', 
    component: AdminDashboardComponent, 
    //canActivate: [RoleGuard], 
    //data: { roles: ['ADMIN'] } 
  },
  { 
    path: 'livres', 
    component: BooksListComponent, 
    //canActivate: [RoleGuard], 
    //data: { roles: ['ADMIN'] } 
  },
  { 
    path: 'livres/ajout', 
    component: BookFormComponent, 
    canActivate: [RoleGuard], 
    data: { roles: ['ADMIN'] } 
  },
  { 
    path: 'livres/modifier/:id', 
    component: BookFormComponent, 
    canActivate: [RoleGuard], 
    data: { roles: ['ADMIN'] } 
  },
  
  // Routes BIBLIOTHECAIRE
  { 
    path: 'bibliothecaire', 
    component: BookSearchComponent, 
    canActivate: [RoleGuard], 
    data: { roles: ['BIBLIOTHECAIRE'] } 
  },
  
  // Routes MEMBRE
  { 
    path: 'membre', 
    component: LoanHistoryComponent, 
    canActivate: [RoleGuard], 
    data: { roles: ['MEMBRE'] } 
  },
  
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' },
  { path: 'emprunt', component: CheckoutComponent, canActivate: [RoleGuard], data: { roles: ['BIBLIOTHECAIRE', 'ADMIN'] } },
  { path: 'retour', component: ReturnBookComponent, canActivate: [RoleGuard], data: { roles: ['BIBLIOTHECAIRE', 'ADMIN'] } },
  { path: 'bibliothecaire', component: BibliothecaireDashboardComponent, canActivate: [RoleGuard], data: { roles: ['BIBLIOTHECAIRE', 'ADMIN'] } },
{path: 'emprunt', component: CheckoutComponent, canActivate: [RoleGuard], data: { roles: ['ADMIN', 'BIBLIOTHECAIRE'] } }
];






