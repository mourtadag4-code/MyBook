import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-emprunt-form',
  standalone: true,
  imports: [FormsModule, CommonModule], // ✅ ngModel + directives
  templateUrl: './emprunt-form.html'
})
export class EmpruntFormComponent {

  emprunt = {
    livreId: '',
    lecteurId: '',
    dateRetour: ''
  };

  onSubmit() {
    console.log(this.emprunt);
  }
}