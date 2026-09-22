export interface GestionDotacion {
  id: number;
  anio: number;
  nombre?: string;
  descripcion?: string;
  mesesCargados: number;
  totalMeses: number;
  tieneDatos: boolean;
  tieneBusinessPlan?: boolean;
  comparar: boolean;
  sucursal?: string;
  ciudad?: string;
}

export interface MesDotacion {
  nombre: string;
  empleados: number;
  disponible: boolean;
  cargado: boolean;
  estado?: EstadoMesDotacion;
}

export type EstadoMesDotacion =
  | 'BP disponible'
  | 'Datos reales pendientes'
  | 'Sincronizando'
  | 'Sincronizado'
  | 'Error de sincronización';

export type FuenteDotacion = 'business-plan' | 'datos-reales';

export interface VistaPreviaImportacion {
  headers: string[];
  rows: Record<string, string | number | boolean | null>[];
  registrosDetectados: number;
  camposMapeados: number;
  advertencias: string[];
}

export interface GestionDetalle {
  id: number;
  anio: number;
  sucursal: string;
  ciudad: string;
  mesesCargados: number;
  totalMeses: number;
  meses: MesDotacion[];
}

export interface EmpleadoDotacion {
  id: number;
  nombre: string;
  ci: string;
  tipo: string;
  categoria: string;
  centroCosto: string;
  cargo: string;
  area: string;
  fechaIngreso: string;
  correo: string;
  direccion: string;
  telefono: string;
  seguro: string;
  contactoEmerg: string;
  nua: string;
  afp: string;
  planta: string;
  turno: string;
  jornada: string;
  banco: string;
  cuentaBancaria: string;
  salarioBase: number;
  bonoAntiguedad: number;
  bonoProduccion: number;
  estado: string;
  fechaBaja?: string;
  motivoBaja?: string;
  familiaCargo: number;
  movimiento?: 'Adición' | 'Desvinculación' | 'Ninguno';
}

export interface MovimientoPlanificado {
  id: number;
  tipo: 'Adición' | 'Desvinculación' | 'Desfase' | 'Vacante';
  nombre: string;
  cargo: string;
  area: string;
  planta: string;
  observaciones: string;
}
