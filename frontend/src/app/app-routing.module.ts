import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormComponent } from './form/form.component';
import { ApplicantLoginComponent } from './applicant-login/applicant-login.component';
import { RecruiterLoginComponent } from './recruiter-login/recruiter-login.component';
import { ApplicantSuccessComponent } from './applicant-success/applicant-success.component'
import { DashboardComponent } from './dashboard/dashboard.component'
import { ApplicantDetailsComponent } from './applicant-details/applicant-details.component'
import { AuthGuard } from './authentication/auth.guard';
import { NotFoundComponent } from './not-found/not-found.component'


const routes: Routes = [
  // Applicant routes
  { path: 'applicant/login', component: ApplicantLoginComponent }, // ignore this!!!
  { path: 'applicant/form', component: FormComponent, canActivate: [AuthGuard]}, // , data: { roles: ['applicant-main'] }
  { path: 'applicant/submit', component: ApplicantSuccessComponent, canActivate: [AuthGuard],},

  // Recruiter routes
  { path: 'recruiter/login', component: RecruiterLoginComponent },
  { path: 'recruiter/dashboard', component: DashboardComponent, canActivate: [AuthGuard]}, // , data: { roles: ['recruiter-main'] }
  { path: 'recruiter/applicantDetails/:id', component: ApplicantDetailsComponent, canActivate: [AuthGuard]},

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

