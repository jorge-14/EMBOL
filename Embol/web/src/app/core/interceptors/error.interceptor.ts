import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { LoggerService } from '../services/logger.service';

/**
 * Singleton — Interceptor global de errores HTTP.
 *
 * Captura TODOS los errores HTTP de la aplicación y:
 *  1. Los loguea de forma centralizada via LoggerService
 *  2. Transforma errores del backend a mensajes legibles
 *  3. Maneja casos especiales (401 → sesión expirada, 403 → forbidden, 0 → sin conexión)
 *
 * Registrado como Singleton en app.config.ts via HTTP_INTERCEPTORS.
 */
@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private logger: LoggerService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        let userMessage: string;

        if (error.status === 0) {
          // Error de red / sin conexión
          userMessage = 'No se pudo conectar con el servidor. Verifique su conexión a internet.';
          this.logger.error('Network error — no connection', { url: req.url });

        } else if (error.status === 401) {
          // No autenticado — sesión expirada
          userMessage = 'Su sesión ha expirado. Por favor, inicie sesión nuevamente.';
          this.logger.warn('Unauthorized (401)', { url: req.url });

        } else if (error.status === 403) {
          // Sin permisos
          userMessage = 'No tiene permisos para realizar esta acción.';
          this.logger.warn('Forbidden (403)', { url: req.url });

        } else if (error.status === 404) {
          // Recurso no encontrado
          userMessage = 'El recurso solicitado no fue encontrado.';
          this.logger.warn('Not Found (404)', { url: req.url });

        } else if (error.status >= 500) {
          // Error de servidor
          userMessage = 'Error interno del servidor. Intente nuevamente más tarde.';
          this.logger.error(`Server Error (${error.status})`, {
            url: req.url,
            body: error.error,
          });

        } else {
          // Otros errores
          userMessage = error.error?.message || `Error inesperado (${error.status}).`;
          this.logger.error(`HTTP Error (${error.status})`, {
            url: req.url,
            message: error.message,
          });
        }

        // Re-throw con mensaje legible para que los componentes lo consuman
        return throwError(() => new Error(userMessage));
      })
    );
  }
}
