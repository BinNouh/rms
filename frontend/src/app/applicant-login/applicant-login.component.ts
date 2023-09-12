import {Component, inject, OnInit} from '@angular/core';
import {AuthService} from "../authentication/auth.service";

import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-applicant-login',
  templateUrl: './applicant-login.component.html',
  styleUrls: ['./applicant-login.component.css']
})
export class ApplicantLoginComponent implements OnInit {
  http = inject(HttpClient);

  constructor(private authService: AuthService) {}

  login(): void {
    this.authService.login();
  }

  ngOnInit(): void {
    this.http.get<string>('/applicant/form').subscribe(console.log)
  }

}