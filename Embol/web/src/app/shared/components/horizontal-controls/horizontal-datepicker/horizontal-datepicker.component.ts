import { Component, input, model, signal, HostListener, ElementRef, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-horizontal-datepicker',
  standalone: true,
  host: {
    'class': 'block'
  },
  imports: [CommonModule, FormsModule],
  templateUrl: './horizontal-datepicker.component.html'
})
export class HorizontalDatepickerComponent {
  id = input<string>(`date-${Math.random().toString(36).substr(2, 9)}`);
  label = input<string>('');
  placeholder = input<string>('Seleccione una fecha...');

  // Two-way binding signal (YYYY-MM-DD string)
  value = model<string | null>(null);

  isOpen = signal<boolean>(false);

  // State for the calendar view
  currentDate = signal<Date>(new Date());

  viewDate = computed(() => {
    const d = this.currentDate();
    return {
      month: d.getMonth(),
      year: d.getFullYear(),
      monthName: d.toLocaleString('es-ES', { month: 'long' })
    };
  });

  daysInMonth = computed(() => {
    const d = this.currentDate();
    const year = d.getFullYear();
    const month = d.getMonth();

    const firstDay = new Date(year, month, 1).getDay();
    const days = new Date(year, month + 1, 0).getDate();

    // Adjust firstDay for Monday start if needed, but standard getDay() is 0 (Sun) to 6 (Sat)
    // We'll use Sunday start for simplicity, or adjust for Monday. Let's use Sunday for now.

    const calendarDays: { day: number | null, dateStr: string | null }[] = [];

    // Padding for first week
    for (let i = 0; i < firstDay; i++) {
      calendarDays.push({ day: null, dateStr: null });
    }

    for (let i = 1; i <= days; i++) {
      const date = new Date(year, month, i);
      calendarDays.push({
        day: i,
        dateStr: date.toISOString().split('T')[0]
      });
    }

    return calendarDays;
  });

  weekDays = ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sá'];

  constructor(private eRef: ElementRef) {}

  @HostListener('document:click', ['$event'])
  clickout(event: Event) {
    if(!this.eRef.nativeElement.contains(event.target)) {
      if (this.isOpen()) {
        this.isOpen.set(false);
      }
    }
  }

  toggleOpen() {
    if (!this.isOpen() && this.value()) {
        // Al abrir, si hay valor, centrar calendario en ese valor
        const d = new Date(this.value()! + 'T00:00:00');
        if (!isNaN(d.getTime())) {
            this.currentDate.set(d);
        }
    }
    this.isOpen.update(v => !v);
  }

  prevMonth() {
    const d = this.currentDate();
    this.currentDate.set(new Date(d.getFullYear(), d.getMonth() - 1, 1));
  }

  nextMonth() {
    const d = this.currentDate();
    this.currentDate.set(new Date(d.getFullYear(), d.getMonth() + 1, 1));
  }

  selectDate(dateStr: string | null) {
    if (dateStr) {
      this.value.set(dateStr);
      this.isOpen.set(false);
    }
  }

  clear() {
    this.value.set(null);
    this.isOpen.set(false);
  }

  formatDisplayDate(): string {
    const val = this.value();
    if (!val) return this.placeholder();

    const d = new Date(val + 'T00:00:00');
    if (isNaN(d.getTime())) return this.placeholder();

    return d.toLocaleDateString('es-ES', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
    });
  }

  isToday(dateStr: string | null): boolean {
    if (!dateStr) return false;
    const today = new Date().toISOString().split('T')[0];
    return dateStr === today;
  }

  isSelected(dateStr: string | null): boolean {
    if (!dateStr) return false;
    return this.value() === dateStr;
  }
}
