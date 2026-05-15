import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { BooksListComponent } from './components/books-list/books-list.component';
import { BookFormComponent } from './components/book-form/book-form.component';


export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'admin', component: AdminDashboardComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'livres', component: BooksListComponent },
  { path: 'livres/ajout', component: BookFormComponent },
  { path: 'livres/modifier/:id', component: BookFormComponent },
  { path: '**', redirectTo: '/login' }
];

