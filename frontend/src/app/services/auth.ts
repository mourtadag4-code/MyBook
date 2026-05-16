import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Auth {


   logout(): void {
  localStorage.removeItem('token');
  localStorage.removeItem('user_info');
  window.location.href = '/login';
  }
  
}
