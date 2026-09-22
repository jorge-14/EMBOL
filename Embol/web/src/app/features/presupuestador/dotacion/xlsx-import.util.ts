import { EmpleadoDotacion, VistaPreviaImportacion } from './models/dotacion.model';

type CellValue = string | number | boolean | null;

const employeeFields: Record<string, keyof EmpleadoDotacion> = {
  nombre: 'nombre', empleado: 'nombre', name: 'nombre',
  ci: 'ci', carnet: 'ci', documento: 'ci',
  tipo: 'tipo', categoria: 'categoria',
  centrocosto: 'centroCosto', centrodecosto: 'centroCosto',
  cargo: 'cargo', area: 'area',
  fechaingreso: 'fechaIngreso', correo: 'correo', email: 'correo',
  direccion: 'direccion', telefono: 'telefono', seguro: 'seguro',
  contactoemergencia: 'contactoEmerg', contactoemerg: 'contactoEmerg',
  nua: 'nua', afp: 'afp', planta: 'planta', turno: 'turno', jornada: 'jornada',
  banco: 'banco', cuentabancaria: 'cuentaBancaria', cuenta: 'cuentaBancaria',
  salariobase: 'salarioBase', bonoantiguedad: 'bonoAntiguedad',
  bonoproduccion: 'bonoProduccion', estado: 'estado',
  fechabaja: 'fechaBaja', motivobaja: 'motivoBaja',
  familiacargo: 'familiaCargo', familiaacargo: 'familiaCargo', movimiento: 'movimiento'
};

function normalize(value: string): string {
  return value.normalize('NFD').replace(/[\u0300-\u036f]/g, '').replace(/[^a-zA-Z0-9]/g, '').toLowerCase();
}

async function inflate(data: Uint8Array, method: number): Promise<Uint8Array> {
  if (method === 0) return data;
  if (method !== 8) throw new Error('El XLSX utiliza un método de compresión no compatible.');
  const stream = new Blob([data as BlobPart]).stream().pipeThrough(new DecompressionStream('deflate-raw'));
  return new Uint8Array(await new Response(stream).arrayBuffer());
}

async function unzip(buffer: ArrayBuffer): Promise<Map<string, Uint8Array>> {
  const bytes = new Uint8Array(buffer);
  const view = new DataView(buffer);
  let eocd = -1;
  for (let offset = bytes.length - 22; offset >= Math.max(0, bytes.length - 65557); offset--) {
    if (view.getUint32(offset, true) === 0x06054b50) { eocd = offset; break; }
  }
  if (eocd < 0) throw new Error('El archivo no tiene una estructura XLSX válida.');

  const entries = new Map<string, Uint8Array>();
  const total = view.getUint16(eocd + 10, true);
  let offset = view.getUint32(eocd + 16, true);
  const decoder = new TextDecoder();

  for (let index = 0; index < total; index++) {
    if (view.getUint32(offset, true) !== 0x02014b50) break;
    const method = view.getUint16(offset + 10, true);
    const compressedSize = view.getUint32(offset + 20, true);
    const nameLength = view.getUint16(offset + 28, true);
    const extraLength = view.getUint16(offset + 30, true);
    const commentLength = view.getUint16(offset + 32, true);
    const localOffset = view.getUint32(offset + 42, true);
    const name = decoder.decode(bytes.slice(offset + 46, offset + 46 + nameLength));
    const localNameLength = view.getUint16(localOffset + 26, true);
    const localExtraLength = view.getUint16(localOffset + 28, true);
    const dataStart = localOffset + 30 + localNameLength + localExtraLength;
    entries.set(name, await inflate(bytes.slice(dataStart, dataStart + compressedSize), method));
    offset += 46 + nameLength + extraLength + commentLength;
  }
  return entries;
}

function cellColumn(reference: string): number {
  const letters = reference.match(/[A-Z]+/i)?.[0]?.toUpperCase() ?? 'A';
  return [...letters].reduce((column, letter) => column * 26 + letter.charCodeAt(0) - 64, 0) - 1;
}

export async function parseAnnualBusinessPlan(file: File): Promise<VistaPreviaImportacion> {
  const files = await unzip(await file.arrayBuffer());
  const decoder = new TextDecoder();
  const parser = new DOMParser();
  const sharedXml = files.get('xl/sharedStrings.xml');
  const sharedStrings = sharedXml
    ? [...parser.parseFromString(decoder.decode(sharedXml), 'application/xml').getElementsByTagName('si')]
        .map(item => [...item.getElementsByTagName('t')].map(text => text.textContent ?? '').join(''))
    : [];
  const sheetName = [...files.keys()].filter(name => /^xl\/worksheets\/sheet\d+\.xml$/.test(name)).sort()[0];
  if (!sheetName) throw new Error('No se encontró una hoja de cálculo en el archivo.');

  const sheet = parser.parseFromString(decoder.decode(files.get(sheetName)!), 'application/xml');
  const matrix: CellValue[][] = [...sheet.getElementsByTagName('row')].map(row => {
    const values: CellValue[] = [];
    for (const cell of [...row.getElementsByTagName('c')]) {
      const type = cell.getAttribute('t');
      const raw = cell.getElementsByTagName('v')[0]?.textContent
        ?? cell.getElementsByTagName('t')[0]?.textContent
        ?? '';
      let value: CellValue = raw;
      if (type === 's') value = sharedStrings[Number(raw)] ?? '';
      else if (type === 'b') value = raw === '1';
      else if (type !== 'inlineStr' && raw !== '' && !Number.isNaN(Number(raw))) value = Number(raw);
      values[cellColumn(cell.getAttribute('r') ?? 'A1')] = value;
    }
    return values;
  }).filter(row => row.some(value => value !== undefined && value !== null && value !== ''));

  if (matrix.length < 2) throw new Error('El XLSX no contiene registros para importar.');
  const columnCount = Math.max(...matrix.map(row => row.length));
  const headers = Array.from({ length: columnCount }, (_, index) => String(matrix[0][index] ?? `Campo ${index + 1}`).trim());
  const rows = matrix.slice(1).map(values => Object.fromEntries(headers.map((header, index) => [header, values[index] ?? null])));
  const camposMapeados = headers.filter(header => employeeFields[normalize(header)]).length;
  const advertencias: string[] = [];
  if (!headers.some(header => ['mes', 'month', 'periodo'].includes(normalize(header)))) {
    advertencias.push('No se encontró una columna Mes; los registros se distribuirán en los 12 meses.');
  }
  if (!camposMapeados) advertencias.push('No se reconocieron campos de dotación; revisa los encabezados del archivo.');

  return { headers, rows, registrosDetectados: rows.length, camposMapeados, advertencias };
}

export function distributeBusinessPlan(preview: VistaPreviaImportacion): Record<string, EmpleadoDotacion[]> {
  const monthNames = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];
  const shortMonthNames = ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'];
  const monthHeader = preview.headers.find(header => ['mes', 'month', 'periodo'].includes(normalize(header)));
  const mapped = preview.rows.map((row, index) => {
    const employee: EmpleadoDotacion = {
      id: index + 1, nombre: '', ci: '', tipo: '', categoria: '', centroCosto: '', cargo: '', area: '',
      fechaIngreso: '', correo: '', direccion: '', telefono: '', seguro: '', contactoEmerg: '', nua: '', afp: '',
      planta: '', turno: '', jornada: '', banco: '', cuentaBancaria: '', salarioBase: 0, bonoAntiguedad: 0,
      bonoProduccion: 0, estado: '', familiaCargo: 0, movimiento: 'Ninguno'
    };
    for (const [header, value] of Object.entries(row)) {
      const field = employeeFields[normalize(header)];
      if (!field || value === null) continue;
      (employee as unknown as Record<string, CellValue>)[field] = value;
    }
    employee.id = Number(employee.id) || index + 1;
    employee.salarioBase = Number(employee.salarioBase) || 0;
    employee.bonoAntiguedad = Number(employee.bonoAntiguedad) || 0;
    employee.bonoProduccion = Number(employee.bonoProduccion) || 0;
    employee.familiaCargo = Number(employee.familiaCargo) || 0;
    return { employee, month: monthHeader ? row[monthHeader] : null };
  });

  return Object.fromEntries(monthNames.map((month, monthIndex) => {
    if (!monthHeader) return [month, mapped.map(item => ({ ...item.employee }))];
    const aliases = [normalize(month), normalize(shortMonthNames[monthIndex]), String(monthIndex + 1), String(monthIndex + 1).padStart(2, '0')];
    return [month, mapped.filter(item => aliases.includes(normalize(String(item.month ?? '')))).map(item => ({ ...item.employee }))];
  }));
}
