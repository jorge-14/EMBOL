import { Component, input, model, signal, HostListener, ElementRef, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SelectOption } from '../models/select-option.model';

@Component({
  selector: 'app-horizontal-multi-select',
  standalone: true,
  host: {
    'class': 'block'
  },
  imports: [CommonModule, FormsModule],
  templateUrl: './horizontal-multi-select.component.html'
})
export class HorizontalMultiSelectComponent {
  id = input<string>(`multi-select-${Math.random().toString(36).substr(2, 9)}`);
  label = input<string>('');
  options = input<SelectOption[]>([]);

  // Two-way binding signal para múltiples valores
  value = model<any[]>([]);

  isOpen = signal<boolean>(false);
  searchText = signal<string>('');

  filteredOptions = computed(() => {
    const text = this.searchText().toLowerCase();
    return this.options().filter(o => o.label.toLowerCase().includes(text));
  });

  constructor(private eRef: ElementRef) { }

  @HostListener('document:click', ['$event'])
  clickout(event: Event) {
    if (!this.eRef.nativeElement.contains(event.target)) {
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

  isSelected(option: SelectOption): boolean {
    return this.value().includes(option.value);
  }

  toggleSelection(option: SelectOption) {
    const current = this.value();
    if (this.isSelected(option)) {
      this.value.set(current.filter(val => val !== option.value));
    } else {
      this.value.set([...current, option.value]);
    }
  }

  getDisplayValue(): string {
    const current = this.value();
    if (current.length === 0) return 'Seleccionar...';
    if (current.length === 1) {
      const opt = this.options().find(o => o.value === current[0]);
      return opt ? opt.label : '';
    }
    return `${current.length} seleccionados`;
  }
}
