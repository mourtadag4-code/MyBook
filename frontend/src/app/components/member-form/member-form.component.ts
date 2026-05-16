import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AdminService } from '../../services/admin.service';

@Component({
    selector: 'app-member-form',
    standalone: true,
    imports: [ReactiveFormsModule, CommonModule, RouterLink],
    templateUrl: './member-form.component.html',
    styleUrls: ['./member-form.component.css']
})
export class MemberFormComponent implements OnInit {
    memberForm: FormGroup;
    isEditMode = false;
    memberId: number | null = null;

    constructor(
        private fb: FormBuilder,
        private adminService: AdminService,
        private route: ActivatedRoute,
        private router: Router
    ) {
        this.memberForm = this.fb.group({
            nom: ['', Validators.required],
            prenom: ['', Validators.required],
            email: ['', [Validators.required, Validators.email]],
            telephone: [''],
            dateNaissance: [''],
            adresse: [''],
            password: ['', [Validators.required, Validators.minLength(6)]]
        });
    }

    ngOnInit(): void {
        const id = this.route.snapshot.paramMap.get('id');
        if (id) {
            this.isEditMode = true;
            this.memberId = +id;
            this.memberForm.get('password')?.clearValidators();
            this.memberForm.get('password')?.updateValueAndValidity();
            this.adminService.getMemberById(this.memberId).subscribe(member => {
                this.memberForm.patchValue(member);
            });
        }
    }

    onSubmit(): void {
        if (this.memberForm.valid) {
            if (this.isEditMode && this.memberId) {
                this.adminService.updateMember(this.memberId, this.memberForm.value).subscribe({
                    next: () => this.router.navigate(['/admin/membres']),
                    error: (err) => console.error('Erreur:', err)
                });
            } else {
                this.adminService.createMember(this.memberForm.value).subscribe({
                    next: () => this.router.navigate(['/admin/membres']),
                    error: (err) => console.error('Erreur:', err)
                });
            }
        }
    }
}