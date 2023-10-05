import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ApplicantService } from './applicant.service';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component'
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  applicants: any[] = [];

  public firstName: string = '';
  public lastName: string = '';

  gender: string = "";
  submissionStatus: string = "";

  constructor(private applicantService: ApplicantService, private keycloakService: KeycloakService, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadApplicants();
    this.getUserDetails();
  }

  loadApplicants(): void {
    this.applicantService.getAllApplicants().subscribe(data => {
      this.applicants = data;
    });
  }

  private getUserDetails(): void {
    const userDetails = this.keycloakService.getKeycloakInstance().tokenParsed;
    if (userDetails) {
      this.firstName = userDetails['given_name'] || ''; // 'given_name' usually contains the first name in Keycloak.
      this.lastName = userDetails['family_name'] || ''; // 'family_name' usually contains the last name in Keycloak.
    }
}


  addNewApplicant(newApplicant: any): void {
    this.applicantService.addApplicant(newApplicant).subscribe(applicant => {
      this.applicants.push(applicant);
    });
  }

  updateApplicantStatus(id: number, event: Event): void {
    const target = event.target as HTMLSelectElement;
    const status = target.value;
    
    this.applicantService.updateApplicantStatus(id, status).subscribe(
      updatedApplicant => {
        const index = this.applicants.findIndex(applicant => applicant.id === updatedApplicant.id);
        if (index !== -1) {
          this.applicants[index] = updatedApplicant;
        }
      },
      error => {
        console.error('Error updating the status:', error);
      }
    );
  }
  

  confirmDelete(id: number): void {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: '250px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleteApplicant(id);
      }
    });
  }

  deleteApplicant(id: number): void {
    console.log("Attempting to delete applicant with id:", id, "Type of ID:", typeof id);
    this.applicantService.deleteApplicant(id).subscribe(
      () => {
        const index = this.applicants.findIndex(applicant => applicant.id === id);
        if (index !== -1) {
          this.applicants.splice(index, 1);
        }
      },
      error => {
        console.error('Error deleting the applicant:', error);
        // Optionally, display a user-friendly error message to the user
      }
    );
  }

  filterData(): void {
    this.applicantService.filterApplicants(this.gender, this.submissionStatus).subscribe(data => {
      this.applicants = data;
    });
  }

  logout(): void {
    this.keycloakService.logout('http://localhost:4200');  // Replace with your desired redirect URL after logout.
  }
  
}