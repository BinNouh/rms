import { Component, OnInit } from '@angular/core';
import { ApplicantService } from './applicant.service';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  applicants: any[] = [];

  public username: string = '';

  constructor(private applicantService: ApplicantService, private keycloakService: KeycloakService) {}

  ngOnInit(): void {
    this.loadApplicants();
    this.getUsername();
  }

  loadApplicants(): void {
    this.applicantService.getAllApplicants().subscribe(data => {
      this.applicants = data;
    });
  }

  private getUsername(): void {
    const userDetails = this.keycloakService.getKeycloakInstance().tokenParsed;
    if (userDetails && userDetails['preferred_username']) {
      this.username = userDetails['preferred_username'];
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



  filterApplicantsByGender(event: Event): void {
    const target = event.target as HTMLSelectElement;
    const gender = target.value;
    this.applicantService.filterApplicantsByGender(gender).subscribe(filteredApplicants => {
      this.applicants = filteredApplicants;
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


  filterApplicantsByStatus(event: Event): void {
    const target = event.target as HTMLSelectElement;
    const status = target.value;
    this.applicantService.filterApplicantsByStatus(status).subscribe(filteredApplicants => {
      this.applicants = filteredApplicants;
    });
  }

  logout(): void {
    this.keycloakService.logout('http://localhost:4200');  // Replace with your desired redirect URL after logout.
  }
  

  // Implement other methods as per requirements
}