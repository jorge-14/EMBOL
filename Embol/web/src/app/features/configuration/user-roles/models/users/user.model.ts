// ─── User Model ───────────────────────────────────────────────────────────────
// El ID es `any` hasta confirmar si el backend usa Long o UUID.

export interface UserRow {
  id: any;
  initials: string;
  color: string;
  usuario: string;
  nombreCompleto: string;
  correo: string;
  planta: string;
  cargo: string;
  rol: string;
  grupo: string;
  estado: string;
}
