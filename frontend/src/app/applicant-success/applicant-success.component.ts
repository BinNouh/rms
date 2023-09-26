import { Component } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
@Component({
  selector: 'app-applicant-success',
  templateUrl: './applicant-success.component.html',
  styleUrls: ['./applicant-success.component.css']
})
export class ApplicantSuccessComponent {

  constructor(private keycloakService: KeycloakService) {}

  logout(): void {
    this.keycloakService.logout('http://localhost:4200');  // Replace with your desired redirect URL after logout.
  }

}
