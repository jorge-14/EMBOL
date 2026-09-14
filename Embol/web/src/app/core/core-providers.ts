// ─── Core Providers — Singletons ───
//
// Agrupa TODOS los providers singleton del core/ para registrar
// una sola vez en app.config.ts.
//
// En Angular 22 standalone, no existe "CoreModule" que se importe una vez.
// En su lugar, exportamos un array de providers que se inyecta
// directamente en la configuración de la app.
//
// Uso en app.config.ts:
//   providers: [ ...CORE_PROVIDERS, ...otros ]

import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { APP_INITIALIZER } from '@angular/core';

import { ErrorInterceptor } from './interceptors/error.interceptor';
import { ConfigService } from './services/config.service';

// ── Factory: cargar config de la app (opcional) ──
function initializeConfig(configService: ConfigService) {
  return () => configService.loadFromAssets('/assets/app-config.json');
}

/**
 * CORE_PROVIDERS — Todos los providers Singleton de la aplicación.
 *
 * Incluye:
 *  - ErrorInterceptor (manejo global de errores HTTP)
 *  - ConfigService inicialización
 *
 * Se registra UNA SOLA VEZ en app.config.ts.
 * Equivalente al antiguo CoreModule.forRoot().
 */
export const CORE_PROVIDERS = [
  // ── Config (carga opcional desde assets) ──
  {
    provide: APP_INITIALIZER,
    useFactory: initializeConfig,
    multi: true,
    deps: [ConfigService],
  },


  {
    provide: HTTP_INTERCEPTORS,
    useClass: ErrorInterceptor,
    multi: true,
  },
];
