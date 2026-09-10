import { Pipe, PipeTransform } from '@angular/core';

/**
 * Pipe para formatear números en moneda boliviana (Bs).
 * Usa punto (.) como separador de miles — convención Bolivia/Latam.
 *
 * Uso: {{ 18500 | currencyBo }}        → "Bs 18.500"
 *      {{ 1171090 | currencyBo }}       → "Bs 1.171.090"
 *      {{ 18500 | currencyBo:true }}    → "Bs 18.500,00"
 */
@Pipe({
  name: 'currencyBo',
  standalone: true
})
export class CurrencyBoPipe implements PipeTransform {
  transform(
    value: number | string | null | undefined,
    showDecimals: boolean = false
  ): string {
    if (value === null || value === undefined || value === '') {
      return '';
    }

    const num = typeof value === 'string' ? parseFloat(value) : value;
    if (isNaN(num)) {
      return '';
    }

    // Formato: punto para miles, coma para decimales
    let formatted: string;
    if (showDecimals) {
      formatted = num
        .toFixed(2)
        .replace('.', ',')
        .replace(/\B(?=(\d{3})+(?!\d))/g, '.');
    } else {
      const intPart = Math.round(num).toString();
      formatted = intPart.replace(/\B(?=(\d{3})+(?!\d))/g, '.');
    }

    return `Bs ${formatted}`;
  }
}
