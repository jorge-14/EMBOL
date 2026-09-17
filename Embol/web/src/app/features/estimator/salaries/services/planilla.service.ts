import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, delay } from 'rxjs';
import { PlanillaRow, CargaSocial, PlanillaSummary } from '../models/planilla.model';
import { Page } from '../../../../shared/models/pagination.model';

/**
 * Servicio HTTP para Planilla — Sueldos y Salarios.
 * Actualmente devuelve datos mock que reflejan la imagen de referencia.
 * Reemplazar los métodos por llamadas reales al backend.
 */
@Injectable({
  providedIn: 'root'
})
export class PlanillaService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/v1/planilla';

  getPlanillas(page: number = 0, size: number = 8, area?: string, cargo?: string): Observable<Page<PlanillaRow>> {
    // TODO: Reemplazar con llamada real
    return of(this.getMockPlanillasPage(page, size, area, cargo)).pipe(delay(600));
  }

  getSummary(): Observable<PlanillaSummary> {
    return of({
      totalPlanillaAnual: 817800,
      totalCargasSociales: 148921,
      totalProvisiones: 204368,
      granTotalCMO: 1171090,
    }).pipe(delay(600));
  }

  getCargasSociales(): Observable<CargaSocial[]> {
    return of([
      { concepto: 'AFP Patronal',        porcentaje: 7.21,  base: 'Haber Básico', montoMensual: 4914,  montoAnual: 58963  },
      { concepto: 'CPS (Caja Salud)',    porcentaje: 10.00, base: 'Haber Básico', montoMensual: 6815,  montoAnual: 81780  },
      { concepto: 'INFOCAL',             porcentaje: 1.00,  base: 'Haber Básico', montoMensual: 682,   montoAnual: 8178   },
      { concepto: 'Indemnización',       porcentaje: 8.33,  base: 'Haber Básico', montoMensual: 5677,  montoAnual: 68123  },
      { concepto: 'Aguinaldo',           porcentaje: 8.33,  base: 'Haber Básico', montoMensual: 5677,  montoAnual: 68123  },
      { concepto: 'Prima',               porcentaje: 8.33,  base: 'Haber Básico', montoMensual: 5677,  montoAnual: 68123  },
    ]).pipe(delay(600));
  }

  saveChanges(changes: { id: string | number; changes: Record<string, any> }[]): Observable<any> {
    // TODO: Reemplazar con: return this.http.patch(this.apiUrl, { changes });
    console.log('Guardando cambios:', changes);
    return of({ success: true }).pipe(delay(500));
  }

  // ─── Datos Mock ────────────────────────────────────────────
  private getMockPlanillasPage(page: number, size: number, area?: string, cargo?: string): Page<PlanillaRow> {
    const allRows: PlanillaRow[] = [
      { id: 1, nroPers: 1001, nombre: 'Carlos Rodríguez',    familia: 'A', cargo: 'Gerente de Planta',          area: 'ADM',  haberBasico: 18500, ene: 18500, feb: 18500, mar: 18500, abr: 18500, may: 18500, jun: 18500, jul: 18500, ago: 18500, sep: 18500, oct: 18500, nov: 18500, dic: 18500, totalAnual: 222000, cargasPorcentaje: 47952 },
      { id: 2, nroPers: 1042, nombre: 'Lucía Fernández',     familia: 'B', cargo: 'Jefa de RRHH',               area: 'ADM',  haberBasico: 12800, ene: 12800, feb: 12800, mar: 12800, abr: 12800, may: 12800, jun: 12800, jul: 12800, ago: 12800, sep: 12800, oct: 12800, nov: 12800, dic: 12800, totalAnual: 153600, cargasPorcentaje: 33178 },
      { id: 3, nroPers: 1086, nombre: 'Roberto Villanueva',  familia: 'C', cargo: 'Supervisor Producción',      area: 'PROD', haberBasico: 8500,  ene: 8500,  feb: 8500,  mar: 8500,  abr: 8500,  may: 8500,  jun: 8500,  jul: 8500,  ago: 8500,  sep: 8500,  oct: 8500,  nov: 8500,  dic: 8500,  totalAnual: 102000, cargasPorcentaje: 22032 },
      { id: 4, nroPers: 1120, nombre: 'Ana Mamani',          familia: 'D', cargo: 'Operaria Línea K-108',       area: 'PROD', haberBasico: 4200,  ene: 4200,  feb: 4200,  mar: 4200,  abr: 4200,  may: 4200,  jun: 4200,  jul: 4200,  ago: 4200,  sep: 4200,  oct: 4200,  nov: 4200,  dic: 4200,  totalAnual: 50400,  cargasPorcentaje: 10886 },
      { id: 5, nroPers: 1135, nombre: 'Pedro Quispe',        familia: 'E', cargo: 'Operario Línea K-90',        area: 'PROD', haberBasico: 3850,  ene: 3850,  feb: 3850,  mar: 3850,  abr: 3850,  may: 3850,  jun: 3850,  jul: 3850,  ago: 3850,  sep: 3850,  oct: 3850,  nov: 3850,  dic: 3850,  totalAnual: 46200,  cargasPorcentaje: 9979  },
      { id: 6, nroPers: 1158, nombre: 'María Condori',       familia: 'F', cargo: 'Vendedora Zonal',            area: 'COM',  haberBasico: 3300,  ene: 3300,  feb: 3300,  mar: 3300,  abr: 3300,  may: 3300,  jun: 3300,  jul: 3300,  ago: 3300,  sep: 3300,  oct: 3300,  nov: 3300,  dic: 3300,  totalAnual: 39600,  cargasPorcentaje: 8554  },
      { id: 7, nroPers: 1172, nombre: 'Jorge Flores',        familia: 'G', cargo: 'Analista Financiero',        area: 'FIN',  haberBasico: 9800,  ene: 9800,  feb: 9800,  mar: 9800,  abr: 9800,  may: 9800,  jun: 9800,  jul: 9800,  ago: 9800,  sep: 9800,  oct: 9800,  nov: 9800,  dic: 9800,  totalAnual: 117600, cargasPorcentaje: 25402 },
      { id: 8, nroPers: 1195, nombre: 'Carmen Ticona',       familia: 'H', cargo: 'Técnica de Mantenimiento',   area: 'MANT', haberBasico: 7200,  ene: 7200,  feb: 7200,  mar: 7200,  abr: 7200,  may: 7200,  jun: 7200,  jul: 7200,  ago: 7200,  sep: 7200,  oct: 7200,  nov: 7200,  dic: 7200,  totalAnual: 86400,  cargasPorcentaje: 18662 },
    ];

    let filteredRows = allRows;
    if (area) {
      filteredRows = filteredRows.filter(r => r.area === area);
    }
    if (cargo) {
      filteredRows = filteredRows.filter(r => r.cargo === cargo);
    }

    const start = page * size;
    const content = filteredRows.slice(start, start + size);

    return {
      content,
      page: {
        number: page,
        size: size,
        totalElements: filteredRows.length,
        totalPages: Math.ceil(filteredRows.length / size)
      }
    };
  }
}
