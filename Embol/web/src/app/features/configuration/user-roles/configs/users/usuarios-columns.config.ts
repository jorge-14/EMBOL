// ─── Columns: Usuarios ───────────────────────────────────────────────────────
import { DataTableColumn } from '../../../../../shared/components/data-table/models/data-table.model';
import { UserRow } from '../../models/users/user.model';

export function buildUsuariosColumns(): DataTableColumn<UserRow>[] {
  return [
    {
      key: 'usuario',
      header: 'Usuario',
      type: 'text',
      minWidth: '180px',
      cssClass: 'font-medium text-gray-800',
      formatter: (_, row) => `${row.initials} · ${row.usuario}`,
    },
    {
      key: 'nombreCompleto',
      header: 'Nombre Completo',
      type: 'text',
      minWidth: '160px',
      cssClass: 'font-semibold text-gray-900',
    },
    {
      key: 'correo',
      header: 'Correo',
      type: 'text',
      minWidth: '200px',
      cssClass: 'text-gray-500',
    },
    {
      key: 'planta',
      header: 'Planta',
      type: 'text',
      width: '120px',
      cssClass: 'text-gray-400',
      formatter: (val) => val || '—',
    },
    {
      key: 'cargo',
      header: 'Cargo',
      type: 'text',
      width: '140px',
      cssClass: 'text-gray-400',
      formatter: (val) => val || '—',
    },
    {
      key: 'roles',
      header: 'Roles',
      type: 'badge',
      width: '150px',
      badgeColorMap: {
        'Administrador':   'bg-[#EDE9FE] text-[#5B21B6] border border-[#DDD6FE]',
        'Oficina Central': 'bg-[#EBF5FF] text-[#1E40AF] border border-[#DBEAFE]',
        'Jefe de Planta':  'bg-[#EBF5FF] text-[#1E40AF] border border-[#DBEAFE]',
        'Finanzas':        'bg-[#F0FDF4] text-[#166534] border border-[#BBF7D0]',
        'Consultor':       'bg-[#F3F4F6] text-[#374151] border border-[#E5E7EB]',
        'Rol Privado':     'bg-[#FEF3C7] text-[#92400E] border border-[#FDE68A]',
      },
      formatter: (val) => val || '—',
    },
    {
      key: 'grupos',
      header: 'Grupos',
      type: 'badge',
      width: '170px',
      badgeColorMap: {
        'IT - Infraestructura': 'bg-[#F3F4F6] text-[#374151] border border-[#E5E7EB]',
        'IT - Soporte':         'bg-[#F3F4F6] text-[#374151] border border-[#E5E7EB]',
        'Marketing Digital':    'bg-[#F3F4F6] text-[#374151] border border-[#E5E7EB]',
        'Finanzas':             'bg-[#F3F4F6] text-[#374151] border border-[#E5E7EB]',
      },
      formatter: (val) => val || '—',
    },
    {
      key: 'estado',
      header: 'Estado',
      type: 'badge',
      width: '100px',
      align: 'center',
      badgeColorMap: {
        'Activo':   'bg-green-50 text-green-700 border border-green-100',
        'Inactivo': 'bg-gray-50  text-gray-500  border border-gray-100',
      },
    },
  ];
}
