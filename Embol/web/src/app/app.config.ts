import { ApplicationConfig, provideZonelessChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptorsFromDi, withInterceptors } from '@angular/common/http';
// import { includeBearerTokenInterceptor, INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG } from 'keycloak-angular';

import { routes } from './app.routes';
import { CORE_PROVIDERS } from './core/core-providers';

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
    provideZonelessChangeDetection(),
    provideRouter(routes),
    provideHttpClient(
      // withInterceptors([includeBearerTokenInterceptor]),
      withInterceptorsFromDi()
    ),
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
