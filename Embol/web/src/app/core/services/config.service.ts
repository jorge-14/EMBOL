import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';

export interface AppConfig {
  apiBaseUrl: string;
  appName: string;
  version: string;
  environment: 'development' | 'staging' | 'production';
}

/**
 * Singleton — Configuración global de la aplicación.
 * Ahora utiliza environment.ts como ÚNICA fuente de verdad.
 * Todo se maneja en el build de Angular (Run-time dynamic config eliminado).
 */
@Injectable({
  providedIn: 'root'
})
export class ConfigService {
  
  get apiBaseUrl(): string {
    return environment.apiBaseUrl;
  }

  get appName(): string {
    return 'EMBOL CMO System'; // O moverlo a environment si lo deseas
  }

  get version(): string {
    return '2.4.0';
  }

  get environment(): string {
    return environment.production ? 'production' : 'development';
  }

  get isProduction(): boolean {
    return environment.production;
  }
}
