import { Injectable, signal } from '@angular/core';

export interface AppConfig {
  apiBaseUrl: string;
  appName: string;
  version: string;
  environment: 'development' | 'staging' | 'production';
}

const DEFAULT_CONFIG: AppConfig = {
  apiBaseUrl: 'http://localhost:8080/api/v1',
  appName: 'EMBOL CMO System',
  version: '2.4.0',
  environment: 'development',
};

/**
 * Singleton — Configuración global de la aplicación.
 * providedIn: 'root' garantiza una ÚNICA instancia.
 *
 * Centraliza todas las configuraciones de la app:
 *  - URL base del API
 *  - Nombre y versión
 *  - Entorno activo
 *
 * Uso:
 *   private config = inject(ConfigService);
 *   const url = this.config.apiBaseUrl;
 */
@Injectable({
  providedIn: 'root'
})
export class ConfigService {
  private _config = signal<AppConfig>(DEFAULT_CONFIG);

  /** Configuración actual (readonly signal) */
  readonly config = this._config.asReadonly();

  /** Accesos directos */
  get apiBaseUrl(): string {
    return this._config().apiBaseUrl;
  }

  get appName(): string {
    return this._config().appName;
  }

  get version(): string {
    return this._config().version;
  }

  get environment(): string {
    return this._config().environment;
  }

  get isProduction(): boolean {
    return this._config().environment === 'production';
  }

  /**
   * Carga configuración desde un JSON externo (e.g. /assets/app-config.json).
   * Útil para cambiar config sin rebuilds — ideal para staging/prod.
   */
  async loadFromAssets(path: string = '/assets/app-config.json'): Promise<void> {
    try {
      const response = await fetch(path);
      if (response.ok) {
        const externalConfig = await response.json();
        this._config.set({ ...DEFAULT_CONFIG, ...externalConfig });
      }
    } catch {
      // Silently fall back to defaults — config file is optional
    }
  }

  /** Actualiza la configuración parcialmente */
  update(partial: Partial<AppConfig>): void {
    this._config.set({ ...this._config(), ...partial });
  }
}
