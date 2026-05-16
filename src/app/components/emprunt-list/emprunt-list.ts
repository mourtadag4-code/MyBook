import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmpruntService } from '../../services/emprunt';

@Component({
  selector: 'app-emprunt-list',
  standalone: true, // ✅ IMPORTANT
  imports: [CommonModule], // ✅ POUR *ngFor
  templateUrl: './emprunt-list.html'
})
export class EmpruntListComponent implements OnInit {

  emprunts: any[] = [];

  constructor(private empruntService: EmpruntService) {}

  ngOnInit() {
    this.loadEmprunts();
  }

  loadEmprunts() {
    this.empruntService.getEmprunts().subscribe((data: any) => {
      this.emprunts = data;
    });
  }

  retourner(id: number) {
    this.empruntService.retournerLivre(id).subscribe(() => {
      alert('Livre retourné');
      this.loadEmprunts();
    });
  }
}