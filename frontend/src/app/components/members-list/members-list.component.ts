import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AdminService, Member } from '../../services/admin.service';

@Component({
    selector: 'app-members-list',
    standalone: true,
    imports: [CommonModule, RouterLink],
    templateUrl: './members-list.component.html',
    styleUrls: ['./members-list.component.css']
})
export class MembersListComponent implements OnInit {
    members: Member[] = [];
    loading = true;

    constructor(private adminService: AdminService) {}

    ngOnInit(): void {
        this.loadMembers();
    }

    loadMembers(): void {
        console.log('Appel API membres...');
        this.adminService.getAllMembers().subscribe({
            next: (data) => {
                console.log('Membres reçus:', data);
                this.members = data;
                this.loading = false;
            },
            error: (err) => {
                console.error('Erreur API:', err);
                this.loading = false;
            }
        });
    }

    deleteMember(id: number): void {
        if (confirm('Supprimer ce membre ?')) {
            this.adminService.deleteMember(id).subscribe({
                next: () => this.loadMembers(),
                error: (err) => console.error('Erreur suppression:', err)
            });
        }
    }
}