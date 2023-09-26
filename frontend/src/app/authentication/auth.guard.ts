import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot,} from '@angular/router';
import {KeycloakAuthGuard, KeycloakService} from 'keycloak-angular';


// ... your imports

@Injectable({
  providedIn: 'root'
})
export class AuthGuard extends KeycloakAuthGuard {
  constructor(
    protected override readonly router: Router,
    private readonly keycloak: KeycloakService) {
    super(router, keycloak);
  }

  public async isAccessAllowed(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Promise<boolean> {

    let authenticated = this.keycloak.getKeycloakInstance().authenticated;
    if (!authenticated) {
      await this.keycloak.login({
        redirectUri: window.location.origin + '/login-redirect',
      });
    }

    // Redirect user based on roles after successful login
    if (state.url === '/login-redirect') {
      if (this.roles.includes('applicant-main')) {
        this.router.navigate(['/applicant/form']);
        return false;
      } else if (this.roles.includes('recruiter-main')) {
        this.router.navigate(['/api/dashboard']);
        return false;
      }
    }

    const requiredRoles = route.data['roles'];

    // Allow the user to proceed if no additional roles are required to access the route.
    if (!(requiredRoles instanceof Array) || requiredRoles.length === 0) {
      return true;
    }

    // Allow the user to proceed if all the required roles are present.
    return requiredRoles.every((role) => this.roles.includes(role));
  }
}
