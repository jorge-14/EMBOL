import { Component, input, model, signal, HostListener, ElementRef, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { SelectOption } from '../models/select-option.model';

@Component({
  selector: 'app-horizontal-select',
  standalone: true,
  host: {
    'class': 'block'
  },
  imports: [CommonModule, FormsModule],
  templateUrl: './horizontal-select.component.html'
})
export class HorizontalSelectComponent {
  id = input<string>(`select-${Math.random().toString(36).substr(2, 9)}`);
  label = input<string>('');
  placeholder = input<string>('Seleccione una opción...');
  options = input<SelectOption[]>([]);
  
  // Two-way binding signal
  value = model<any>(null);

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

  toggleOpen() {
    this.isOpen.update(v => {
      if (v) this.searchText.set(''); // limpia al cerrar
      return !v;
    });
  }

  selectOption(option: SelectOption | null) {
    if (option) {
      this.value.set(option.value);
    } else {
      this.value.set(null);
    }
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
