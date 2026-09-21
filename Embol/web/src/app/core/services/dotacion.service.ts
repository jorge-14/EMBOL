import { Injectable, signal } from '@angular/core';
import { Observable, of } from 'rxjs';
import { GestionDotacion, GestionDetalle, MesDotacion } from '../../features/presupuestador/dotacion/models/dotacion.model';
import { GESTIONES_MOCK, EMPLEADOS_MOCK, MOVIMIENTOS_MOCK } from '../../features/presupuestador/dotacion/configs/dotacion-mock.config';

@Injectable({
  providedIn: 'root'
})
export class DotacionService {

  getGestiones(): Observable<GestionDotacion[]> {
    return of(GESTIONES_MOCK);
  }

  getGestionById(id: number): Observable<GestionDetalle | undefined> {
    const gestion = GESTIONES_MOCK.find(g => g.id === id);
    if (!gestion) return of(undefined);

    const mesesNombres = [
      'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
      'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'
    ];

    const meses: MesDotacion[] = mesesNombres.map((nombre, index) => {
      const isCargado = index < gestion.mesesCargados;
      return {
        nombre,
        cargado: isCargado,
        empleados: isCargado ? Math.floor(Math.random() * (450 - 380) + 380) : 0,
        disponible: true // Modificado para que siempre estén disponibles
      };
    });

    return of({
      id: gestion.id,
      anio: gestion.anio,
      mesesCargados: gestion.mesesCargados,
      totalMeses: gestion.totalMeses,
      sucursal: gestion.sucursal!,
      ciudad: gestion.ciudad!,
      meses
    });
  }

  getEmpleadosMock() {
    return EMPLEADOS_MOCK;
  }

  getMovimientosMock() {
    return MOVIMIENTOS_MOCK;
  }
}
