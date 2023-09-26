import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApplicantService } from '../dashboard/applicant.service';  // Import the service

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

  // Inject the ApplicantService into the component's constructor
  constructor(
    private route: ActivatedRoute,
    private applicantService: ApplicantService  
  ) { }

  ngOnInit(): void {
    // Retrieve the applicantId from the route (suppose it's passed as a parameter)
    const id = this.route.snapshot.paramMap.get('id');
    this.applicantId = id ? +id : 0;  // Fallback to 0 or another default value if id is null
    
    // Fetch the applicant details using the service
    this.getApplicantDetails(this.applicantId);
  }

  getApplicantDetails(id: number): void {
    this.applicantService.getApplicant(id).subscribe(data => {
      this.applicant = data;
      this.address = data.address;
      this.attachments = data.attachments;
      this.dependencies = data.dependencies;
      this.emergencyContacts = data.emergencyContacts;
      this.nationalIdentity = data.nationalIdentity;
    });
  }
}
