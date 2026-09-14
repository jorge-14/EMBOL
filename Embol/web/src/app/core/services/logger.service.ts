import { Injectable, signal } from '@angular/core';

export enum LogLevel {
  DEBUG = 0,
  INFO  = 1,
  WARN  = 2,
  ERROR = 3,
  OFF   = 4,
}

/**
 * Singleton — Logger centralizado de la aplicación.
 * providedIn: 'root' garantiza una ÚNICA instancia global.
 *
 * Uso:
 *   private logger = inject(LoggerService);
 *   this.logger.info('Operación exitosa', { userId: 123 });
 *   this.logger.error('Fallo en petición', error);
 */
@Injectable({
  providedIn: 'root'
})
export class LoggerService {
  private level = signal<LogLevel>(LogLevel.DEBUG);

  setLevel(level: LogLevel): void {
    this.level.set(level);
  }

  debug(message: string, ...data: any[]): void {
    if (this.level() <= LogLevel.DEBUG) {
      console.log(`%c[DEBUG] ${message}`, 'color: #6b7280', ...data);
    }
  }

  info(message: string, ...data: any[]): void {
    if (this.level() <= LogLevel.INFO) {
      console.log(`%c[INFO] ${message}`, 'color: #3b82f6', ...data);
    }
  }

  warn(message: string, ...data: any[]): void {
    if (this.level() <= LogLevel.WARN) {
      console.warn(`%c[WARN] ${message}`, 'color: #f59e0b', ...data);
    }
  }

  error(message: string, ...data: any[]): void {
    if (this.level() <= LogLevel.ERROR) {
      console.error(`%c[ERROR] ${message}`, 'color: #ef4444', ...data);
    }
  }
}
