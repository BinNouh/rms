import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormComponent } from './form/form.component';
import { EmptyRedirectComponent } from './empty-redirect/empty-redirect.component'
import { ApplicantSuccessComponent } from './applicant-success/applicant-success.component'
import { DashboardComponent } from './dashboard/dashboard.component'
import { ApplicantDetailsComponent } from './applicant-details/applicant-details.component'
import { AuthGuard } from './authentication/auth.guard';
import { NotFoundComponent } from './not-found/not-found.component';
import {OfferFormComponent} from './offer-form/offer-form.component';

const routes: Routes = [
  // Placeholder route for role-based redirection after login
  { path: 'login-redirect', component: EmptyRedirectComponent, canActivate: [AuthGuard] },

  // Applicant routes
  { path: 'applicant/offer/form', component: OfferFormComponent, canActivate: [AuthGuard], data: { roles: ['applicant-main'] }}, 
  { path: 'applicant/form', component: FormComponent, canActivate: [AuthGuard], data: { roles: ['applicant-main'] }}, 
  { path: 'applicant/submit', component: ApplicantSuccessComponent, canActivate: [AuthGuard], data: { roles: ['applicant-main'] }},

  // Recruiter routes
  { path: 'api/dashboard', component: DashboardComponent, canActivate: [AuthGuard], data: { roles: ['recruiter-main'] }},
  { path: 'api/applicantDetails/:id', component: ApplicantDetailsComponent, canActivate: [AuthGuard], data: { roles: ['recruiter-main'] }},

 // Default route
 { path: '', redirectTo: '/applicant/form', pathMatch: 'full' }, // Adjust if you have a distinct homepage

  // Handle unrecognized paths
  { path: '**', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
