import { Component, input, output, signal, inject, OnInit, OnChanges, SimpleChanges, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DynamicFormConfig, DynamicFormField } from './models/dynamic-form.model';
import { HorizontalSelectComponent } from '../horizontal-controls/horizontal-select/horizontal-select.component';
import { HorizontalDatepickerComponent } from '../horizontal-controls/horizontal-datepicker/horizontal-datepicker.component';

@Component({
  selector: 'app-dynamic-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, HorizontalSelectComponent, HorizontalDatepickerComponent],
  templateUrl: './dynamic-form.component.html',
})
export class DynamicFormComponent implements OnInit, OnChanges {
  private fb = inject(FormBuilder);

  // --- Inputs ---
  config = input.required<DynamicFormConfig>();
  isOpen = input<boolean>(false);
  initialData = input<any>(null);

  // --- Outputs ---
  formSubmit = output<any>();
  close = output<void>();

  // --- Internal State ---
  form: FormGroup = this.fb.group({});

  constructor() {
    // Rebuild form when config changes
    effect(() => {
      this.buildForm();
    });
  }

  ngOnInit(): void {
    this.buildForm();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['initialData'] && this.initialData()) {
      this.form.patchValue(this.initialData());
    }
  }

  private buildForm(): void {
    const group: any = {};
    this.config().fields.forEach(field => {
      const validators = [];
      if (field.required) {
        validators.push(Validators.required);
      }
      if (field.type === 'email') {
        validators.push(Validators.email);
      }
      if (field.validators) {
        validators.push(...field.validators);
      }
      group[field.key] = [this.initialData()?.[field.key] ?? field.value ?? '', validators];
    });
    this.form = this.fb.group(group);
  }

  onSubmit(): void {
    if (this.form.valid) {
      this.formSubmit.emit(this.form.value);
    } else {
      Object.values(this.form.controls).forEach(control => {
        control.markAsTouched();
      });
    }
  }

  onCancel(): void {
    this.close.emit();
  }

  // --- Helpers ---
  getFieldClasses(field: DynamicFormField): string {
    return field.colSpan === 2 ? 'col-span-2' : 'col-span-1';
  }
}
