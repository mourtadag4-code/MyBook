import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-bibliothecaire-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './bibliothecaire-dashboard.component.html',
  styleUrls: ['./bibliothecaire-dashboard.component.css']
})
export class BibliothecaireDashboardComponent {

  constructor() { }
}