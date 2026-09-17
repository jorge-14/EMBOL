import { inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Router,
  RouterStateSnapshot,
  createUrlTreeFromSnapshot
} from '@angular/router';
/*
import { createAuthGuard, AuthGuardData } from 'keycloak-angular';

const isAccessAllowed = async (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot,
  authData: AuthGuardData
) => {
  const { authenticated, keycloak, grantedRoles } = authData;

  // Force the user to log in if currently unauthenticated.
  if (!authenticated) {
    await keycloak.login({
      redirectUri: window.location.origin + state.url
    });
    return false; // wait for redirect
  }

  // You can also check roles here if needed
  // const requiredRoles = route.data['roles'];
  // if (!requiredRoles || requiredRoles.length === 0) {
  //   return true;
  // }
  // return requiredRoles.some((role) => grantedRoles.realmRoles.includes(role));

  return true;
};

export const AuthGuard = createAuthGuard(isAccessAllowed);
*/

export const AuthGuard = () => true;
