import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApplicantService } from '../dashboard/applicant.service';  // Import the service
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-applicant-details',
  templateUrl: './applicant-details.component.html',
  styleUrls: ['./applicant-details.component.css']
})
export class ApplicantDetailsComponent implements OnInit {
  applicantId: number = 0;
  applicant: any;
  address: any;
  attachments: any[] = [];
  dependencies: any[] = [];
  emergencyContacts: any[] = [];
  nationalIdentity: any;

  public firstName: string = '';
  public lastName: string = '';


  // Inject the ApplicantService into the component's constructor
  constructor(
    private route: ActivatedRoute,
    private applicantService: ApplicantService,
    private keycloakService: KeycloakService
  ) { }

  ngOnInit(): void {
    // Retrieve the applicantId from the route (suppose it's passed as a parameter)
    const id = this.route.snapshot.paramMap.get('id');
    this.applicantId = id ? +id : 0;  // Fallback to 0 or another default value if id is null
    
    // Fetch the applicant details using the service
    this.getApplicantDetails(this.applicantId);
    this.getUserDetails();
  }

  private getUserDetails(): void {
    const userDetails = this.keycloakService.getKeycloakInstance().tokenParsed;
    if (userDetails) {
      this.firstName = userDetails['given_name'] || ''; // 'given_name' usually contains the first name in Keycloak.
      this.lastName = userDetails['family_name'] || ''; // 'family_name' usually contains the last name in Keycloak.
    }
}

  getApplicantDetails(id: number): void {
    this.applicantService.getApplicant(id).subscribe(
      data => {
        console.log(data);
        this.applicant = data;
        this.address = data.address;
        this.attachments = data.attachments;
        this.dependencies = data.dependencies;
        this.emergencyContacts = data.emergencyContacts;
        this.nationalIdentity = data.nationalIdentity;
      },
      error => {
        console.error('There was an error fetching the data:', error);
      }
    );
  }

  logout(): void {
    this.keycloakService.logout('http://localhost:4200');  // Replace with your desired redirect URL after logout.
  }
  
}
