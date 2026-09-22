import { GestionDotacion, EmpleadoDotacion, MovimientoPlanificado } from '../models/dotacion.model';

export const GESTIONES_MOCK: GestionDotacion[] = [
  { id: 1, anio: 2024, mesesCargados: 0, totalMeses: 12, tieneDatos: false, tieneBusinessPlan: true, comparar: false, sucursal: 'CBB', ciudad: 'Cochabamba' },
  { id: 2, anio: 2025, mesesCargados: 12, totalMeses: 12, tieneDatos: true, tieneBusinessPlan: true, comparar: true, sucursal: 'CBB', ciudad: 'Cochabamba' },
  { id: 3, anio: 2026, mesesCargados: 9, totalMeses: 12, tieneDatos: true, tieneBusinessPlan: true, comparar: true, sucursal: 'CBB', ciudad: 'Cochabamba' },
];

export const EMPLEADOS_MOCK: EmpleadoDotacion[] = [
  {
    id: 1, nombre: 'Carlos Medina Quispe', ci: '6234571', tipo: 'Permanente', categoria: 'Técnico', centroCosto: 'CC-02', cargo: 'Técnico Logística', area: 'LOG',
    fechaIngreso: '15-Jun-2020', correo: 'c.medina@embol.bo', direccion: 'Calle Jordán 450', telefono: '76523410', seguro: 'CNS', contactoEmerg: 'Rosa Medina 71234560',
    nua: '234567890', afp: 'BBVA Previsión', planta: 'CBB', turno: 'Mañana', jornada: '8h', banco: 'BBVA', cuentaBancaria: '20000023456', salarioBase: 6500,
    bonoAntiguedad: 200, bonoProduccion: 800, estado: 'Activo', familiaCargo: 3, movimiento: 'Ninguno'
  },
  {
    id: 2, nombre: 'Laura Vásquez Peña', ci: '5123456', tipo: 'Por contrato', categoria: 'Comercial', centroCosto: 'CC-03', cargo: 'Vendedora Zonal Norte', area: 'COM',
    fechaIngreso: '01-Ene-2022', correo: 'l.vasquez@embol.bo', direccion: 'Av. Heroínas 870', telefono: '72345678', seguro: 'CNS', contactoEmerg: 'Juan Vásquez 70123456',
    nua: '345678901', afp: 'Futuro de Bolivia', planta: 'CBB', turno: 'Diurno', jornada: '9h', banco: 'Bisa', cuentaBancaria: '30000034567', salarioBase: 4200,
    bonoAntiguedad: 0, bonoProduccion: 900, estado: 'Activo', familiaCargo: 1, movimiento: 'Ninguno'
  },
  {
    id: 3, nombre: 'Roberto Soliz Aliaga', ci: '8345678', tipo: 'Permanente', categoria: 'Ejecutivo', centroCosto: 'CC-01', cargo: 'Supervisor Producción', area: 'PROD MOI',
    fechaIngreso: '10-Feb-2015', correo: 'r.soliz@embol.bo', direccion: 'Pasaje Suipacha', telefono: '71234567', seguro: 'Caja Petrolera', contactoEmerg: 'María Soliz 60987654',
    nua: '456789012', afp: 'BBVA Previsión', planta: 'LPZ', turno: 'Mañana', jornada: '8h', banco: 'BCP', cuentaBancaria: '40000045678', salarioBase: 8500,
    bonoAntiguedad: 850, bonoProduccion: 1200, estado: 'Activo', familiaCargo: 4, movimiento: 'Ninguno'
  },
  {
    id: 4, nombre: 'Sofía Mamani Condori', ci: '9456789', tipo: 'Permanente', categoria: 'Administrativo', centroCosto: 'CC-04', cargo: 'Aux. Administrativa', area: 'ADM',
    fechaIngreso: '20-Ago-2021', correo: 's.mamani@embol.bo', direccion: 'Av. Oquendo 56', telefono: '70123456', seguro: 'CNS', contactoEmerg: 'Luis Mamani 69012345',
    nua: '567890123', afp: 'Futuro de Bolivia', planta: 'CBB', turno: 'Mañana', jornada: '8h', banco: 'BNB', cuentaBancaria: '50000056789', salarioBase: 3500,
    bonoAntiguedad: 175, bonoProduccion: 300, estado: 'Activo', familiaCargo: 0, movimiento: 'Ninguno'
  },
  {
    id: 5, nombre: 'Diego Flores Torrez', ci: '4567890', tipo: 'Por contrato', categoria: 'Operativo', centroCosto: 'CC-02', cargo: 'Operario Línea K-108', area: 'PROD MOD',
    fechaIngreso: '01-Feb-2024', correo: 'd.flores@embol.bo', direccion: 'Calle Hamiraya 7', telefono: '69012345', seguro: 'CNS', contactoEmerg: 'Elena Flores 68901234',
    nua: '678901234', afp: 'Futuro de Bolivia', planta: 'CBB', turno: 'Tarde', jornada: '8h', banco: 'Bisa', cuentaBancaria: '60000067890', salarioBase: 3300,
    bonoAntiguedad: 0, bonoProduccion: 400, estado: 'Activo', familiaCargo: 2, movimiento: 'Adición'
  },
  {
    id: 6, nombre: 'Patricia Choque Ríos', ci: '3678901', tipo: 'Permanente', categoria: 'Técnico', centroCosto: 'CC-03', cargo: 'Analista de Sistemas', area: 'ADM',
    fechaIngreso: '05-May-2018', correo: 'p.choque@embol.bo', direccion: 'Av. América 230', telefono: '68901234', seguro: 'CNS', contactoEmerg: 'Marco Choque 67890123',
    nua: '789012345', afp: 'BBVA Previsión', planta: 'SCZ', turno: 'Mañana', jornada: '9h', banco: 'BBVA', cuentaBancaria: '70000078901', salarioBase: 7200,
    bonoAntiguedad: 720, bonoProduccion: 600, estado: 'Activo', familiaCargo: 1, movimiento: 'Ninguno'
  },
  {
    id: 7, nombre: 'Marco Romero Soria', ci: '2789012', tipo: 'Permanente', categoria: 'Comercial', centroCosto: 'CC-03', cargo: 'Jefe de Ventas Norte', area: 'COM',
    fechaIngreso: '15-Nov-2016', correo: 'm.romero@embol.bo', direccion: 'Av. Ballivián 1100', telefono: '67890123', seguro: 'CNS', contactoEmerg: 'Carla Romero 66789012',
    nua: '890123456', afp: 'Futuro de Bolivia', planta: 'CBB', turno: 'Diurno', jornada: '9h', banco: 'BNB', cuentaBancaria: '80000089012', salarioBase: 9800,
    bonoAntiguedad: 980, bonoProduccion: 1500, estado: 'Baja', fechaBaja: '30-Sep-2026', motivoBaja: 'Renuncia voluntaria', familiaCargo: 3, movimiento: 'Desvinculación'
  },
];

export const MOVIMIENTOS_MOCK: MovimientoPlanificado[] = [
  {
    id: 1, tipo: 'Adición', nombre: 'Diego Flores Torrez', cargo: 'Operario Línea K-108', area: 'PROD MOD',
    planta: 'CBB', observaciones: 'Cobertura expansión línea'
  },
  {
    id: 2, tipo: 'Adición', nombre: 'Ana Limachi Quispe', cargo: 'Analista de Logística', area: 'LOG',
    planta: 'CBB', observaciones: 'Nueva posición aprobada'
  },
  {
    id: 3, tipo: 'Desvinculación', nombre: 'Marco Romero Soria', cargo: 'Jefe de Ventas Norte', area: 'COM',
    planta: 'CBB', observaciones: 'Renuncia voluntaria'
  },
  {
    id: 4, tipo: 'Desfase', nombre: '—', cargo: 'Vendedor Zonal Norte', area: 'COM',
    planta: 'LPZ', observaciones: 'Expansión zona norte'
  },
  {
    id: 5, tipo: 'Vacante', nombre: '—', cargo: 'Supervisor de Producción', area: 'PROD MOI',
    planta: 'SCZ', observaciones: 'Posición nueva en presupuesto'
  }
];
