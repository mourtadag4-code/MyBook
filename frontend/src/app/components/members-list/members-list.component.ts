import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AdminService, Member } from '../../services/admin.service';

@Component({
    selector: 'app-members-list',
    standalone: true,
    imports: [CommonModule, RouterLink, FormsModule],
    templateUrl: './members-list.component.html',
    styleUrls: ['./members-list.component.css']
})
export class MembersListComponent implements OnInit {
    members: Member[] = [];
    loading = true;
    errorMessage = '';
    
    // Variables pour le modal de modification
    showEditModal: boolean = false;
    memberEnModification: Member = {
        id: 0,
        nom: '',
        prenom: '',
        email: '',
        telephone: '',
        adresse: '',
        dateNaissance: '',
        role: 'MEMBRE'
    };

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
                this.errorMessage = 'Impossible de charger les membres';
                this.loading = false;
            }
        });
    }

    // OUVRIRE LE MODALE POUR MODIFIER
    modifierMember(member: Member): void {
        console.log('📝 Modification du membre:', member);
        
        this.memberEnModification = {
            id: member.id,
            nom: member.nom,
            prenom: member.prenom,
            email: member.email,
            telephone: member.telephone || '',
            adresse: member.adresse || '',
            dateNaissance: member.dateNaissance || '',
            role: member.role || 'MEMBRE'
        };
        this.showEditModal = true;
    }

    // ENREGISTRER LES MODIFICATIONS
    enregistrerModification(): void {
        if (!this.memberEnModification.id) return;
        
        this.adminService.updateMember(this.memberEnModification.id, this.memberEnModification).subscribe({
            next: () => {
                alert('Membre modifié avec succès !');
                this.closeEditModal();
                this.loadMembers();
            },
            error: (err) => {
                console.error('Erreur modification:', err);
                alert('Erreur lors de la modification');
            }
        });
    }

    // FERMER LE MODALE
    closeEditModal(): void {
        this.showEditModal = false;
        this.memberEnModification = {
            id: 0,
            nom: '',
            prenom: '',
            email: '',
            telephone: '',
            adresse: '',
            dateNaissance: '',
            role: 'MEMBRE'
        };
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