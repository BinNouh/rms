import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormComponent } from './form/form.component';
import { KeycloakAuthGuard } from 'keycloak-angular';  // <-- import the guard

const routes: Routes = [
  { 
    path: 'form', 
    component: FormComponent, 
    canActivate: [KeycloakAuthGuard]  // <-- use the guard here
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
