import { Component, input, model, signal, HostListener, ElementRef, computed, forwardRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';

import { SelectOption } from '../models/select-option.model';

@Component({
  selector: 'app-horizontal-select',
  standalone: true,
  host: {
    'class': 'block'
  },
  imports: [CommonModule, FormsModule],
  templateUrl: './horizontal-select.component.html',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => HorizontalSelectComponent),
      multi: true
    }
  ]
})
export class HorizontalSelectComponent implements ControlValueAccessor {
  id = input<string>(`select-${Math.random().toString(36).substr(2, 9)}`);
  label = input<string>('');
  labelPosition = input<'horizontal' | 'vertical'>('horizontal');
  labelClass = input<string>('');
  placeholder = input<string>('Seleccione una opción...');
  options = input<SelectOption[]>([]);
  isInvalid = input<boolean>(false);

  // Two-way binding signal
  value = model<any>(null);

  onChange = (val: any) => {};
  onTouched = () => {};

  isOpen = signal<boolean>(false);
  searchText = signal<string>('');

  filteredOptions = computed(() => {
    const text = this.searchText().toLowerCase();
    return this.options().filter(o => o.label.toLowerCase().includes(text));
  });

  constructor(private eRef: ElementRef) {}

  @HostListener('document:click', ['$event'])
  clickout(event: Event) {
    if(!this.eRef.nativeElement.contains(event.target)) {
      if (this.isOpen()) {
        this.searchText.set('');
        this.isOpen.set(false);
      }
    }
  }

  writeValue(val: any): void {
    this.value.set(val);
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  toggleOpen() {
    this.isOpen.update(v => {
      if (!v) {
        this.onTouched(); // mark as touched when opened
      }
      if (v) this.searchText.set(''); // limpia al cerrar
      return !v;
    });
  }

  selectOption(option: SelectOption | null) {
    const val = option ? option.value : null;
    this.value.set(val);
    this.onChange(val);
    this.onTouched();
    this.searchText.set('');
    this.isOpen.set(false);
  }

  getDisplayValue(): string {
    const val = this.value();
    if (val === null || val === undefined || val === '') return this.placeholder();
    const opt = this.options().find(o => o.value === val);
    return opt ? opt.label : this.placeholder();
  }
}
