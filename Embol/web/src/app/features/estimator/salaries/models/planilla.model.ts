// ─── Planilla Domain Models ───

export interface PlanillaRow {
  id: number;
  nroPers: number;
  nombre: string;
  familia: string;
  cargo: string;
  area: string;
  haberBasico: number;
  ene: number;
  feb: number;
  mar: number;
  abr: number;
  may: number;
  jun: number;
  jul: number;
  ago: number;
  sep: number;
  oct: number;
  nov: number;
  dic: number;
  totalAnual: number;
  cargasPorcentaje: number;
}

export interface CargaSocial {
  concepto: string;
  porcentaje: number;
  base: string;
  montoMensual: number;
  montoAnual: number;
}

export interface PlanillaSummary {
  totalPlanillaAnual: number;
  totalCargasSociales: number;
  totalProvisiones: number;
  granTotalCMO: number;
}

