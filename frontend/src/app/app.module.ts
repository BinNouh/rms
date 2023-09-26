import { NgModule, APP_INITIALIZER } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { HttpClientModule } from '@angular/common/http';
import { OAuthModule } from 'angular-oauth2-oidc';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormComponent } from './form/form.component';
import { ApplicantDetailsComponent } from './applicant-details/applicant-details.component';
import { ApplicantSuccessComponent } from './applicant-success/applicant-success.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { KeycloakAngularModule } from 'keycloak-angular';
import { KeycloakService } from 'keycloak-angular';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { EmptyRedirectComponent } from './empty-redirect/empty-redirect.component';

const keycloakService: KeycloakService = new KeycloakService();

function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8080/',
        realm: 'RMS',
        clientId: 'angular-client'
      },
      initOptions: {
        onLoad: 'login-required'
      }
    });
}

@NgModule({
  declarations: [
    AppComponent,
    FormComponent,
    ApplicantSuccessComponent,
    DashboardComponent,
    ApplicantDetailsComponent,
    NotFoundComponent,
    EmptyRedirectComponent,
  ],
  imports: [
    BrowserAnimationsModule,
    MatSnackBarModule,
    KeycloakAngularModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    OAuthModule.forRoot()
  ],
  providers: [{
    // provide: KeycloakService,
    // useValue: keycloakService
    provide: APP_INITIALIZER,
    useFactory: initializeKeycloak,
    multi: true,
    deps: [KeycloakService]
 }],
  bootstrap: [AppComponent]
})
export class AppModule { }
