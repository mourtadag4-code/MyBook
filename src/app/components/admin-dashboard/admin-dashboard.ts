import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-dashboard',
  imports: [CommonModule],
  templateUrl: './admin-dashboard.html',
  styleUrl: './admin-dashboard.css',
})
export class AdminDashboard {
totalBooks: any;
totalMembers: any;
currentLoans: any;
overdueLoans: any;
topBooks: any;
overdueList: any;
}
