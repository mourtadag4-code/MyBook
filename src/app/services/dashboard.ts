import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  apiUrl = 'http://localhost:8080/api'; // ⚠️ ton backend Spring

  constructor(private http: HttpClient) {}

  getTotalBooks() {
    return this.http.get<number>(`${this.apiUrl}/books/count`);
  }

  getTotalMembers() {
    return this.http.get<number>(`${this.apiUrl}/members/count`);
  }

  getCurrentLoans() {
    return this.http.get<any[]>(`${this.apiUrl}/emprunts`);
  }

  getOverdueLoans() {
    return this.http.get<any[]>(`${this.apiUrl}/emprunts/overdue`);
  }

  getTopBooks() {
    return this.http.get<any[]>(`${this.apiUrl}/books/top`);
  }
}