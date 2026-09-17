import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideZonelessChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptorsFromDi, withInterceptors, HTTP_INTERCEPTORS } from '@angular/common/http';
// import { includeBearerTokenInterceptor, INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG } from 'keycloak-angular';
import { providePrimeNG } from 'primeng/config';
import Aura from '@primeng/themes/aura';

import { routes } from './app.routes';
import { CORE_PROVIDERS } from './core/core-providers';

import { MsalInterceptor, MsalGuard, MsalService, MsalBroadcastService, MSAL_INSTANCE, MSAL_GUARD_CONFIG, MSAL_INTERCEPTOR_CONFIG, MsalInterceptorConfiguration, MsalGuardConfiguration } from '@azure/msal-angular';
import { IPublicClientApplication, PublicClientApplication, InteractionType, BrowserCacheLocation } from '@azure/msal-browser';
import { environment } from '../environments/environment';

export function MSALInstanceFactory(): IPublicClientApplication {
  return new PublicClientApplication({
    auth: {
      clientId: environment.entra.clientId,
      authority: `https://login.microsoftonline.com/${environment.entra.tenantId}`,
      redirectUri: environment.entra.redirectUri,
      postLogoutRedirectUri: environment.entra.postLogoutRedirectUri
    },
    cache: {
      cacheLocation: BrowserCacheLocation.LocalStorage
    }
  });
}

export function MSALInterceptorConfigFactory(): MsalInterceptorConfiguration {
  const protectedResourceMap = new Map<string, Array<string>>();
  // Configura aquí la URL de tu backend y los scopes (api://<clientId>/.default)
  protectedResourceMap.set(environment.apiBaseUrl, [`api://${environment.entra.clientId}/.default`]);
  
  return {
    interactionType: InteractionType.Redirect,
    protectedResourceMap
  };
}

export function MSALGuardConfigFactory(): MsalGuardConfiguration {
  return { 
    interactionType: InteractionType.Redirect,
    authRequest: {
      scopes: ['user.read']
    }
  };
}

/**
 * Configuración raíz de la aplicación.
 *
 * CORE_PROVIDERS reemplaza lo que antes era CoreModule.forRoot():
 *  - ErrorInterceptor (manejo global de errores)
 *  - ConfigService (carga config desde assets)
 *
 * Todos son SINGLETONS — se instancian UNA sola vez aquí.
 */
export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZonelessChangeDetection(),
    provideRouter(routes),
    provideHttpClient(
      withInterceptorsFromDi()
    ),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: MsalInterceptor,
      multi: true
    },
    {
      provide: MSAL_INSTANCE,
      useFactory: MSALInstanceFactory
    },
    {
      provide: MSAL_GUARD_CONFIG,
      useFactory: MSALGuardConfigFactory
    },
    {
      provide: MSAL_INTERCEPTOR_CONFIG,
      useFactory: MSALInterceptorConfigFactory
    },
    MsalService,
    MsalGuard,
    MsalBroadcastService,
    providePrimeNG({
      license:
        'eyJpZCI6ImVhZTc1MjBkLTNkZjAtNDBhYy05ZjNmLTc5YmNhYjY1ZmY2OSIsInByb2R1Y3QiOiJwcmltZXVpIiwidGllciI6ImNvbW11bml0eSIsInR5cGUiOiJkZXYiLCJpYXQiOjE3ODkwODA4MjcsImV4cCI6MTgyMDYxNjgyN30.ZfsX2A1GUsP7KQvDqBFhUJipjoAec-B_Tk5LSibnLVugBzo39H_qBOdArFn7ke9FVibddbX2bQow_CH7D5cWDQ',
      theme: {
        preset: Aura,
        options: {
          darkModeSelector: 'none',
          cssLayer: false,
        },
      },
      ripple: true,
    }),
    /*
    {
      provide: INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG,
      useValue: [{ urlPattern: /^(http:\/\/localhost:8080|http:\/\/rocavb:8080)(\/.*)?$/i }]
    },
    */

    // ── Core Singletons ──
    ...CORE_PROVIDERS,
  ]
};


