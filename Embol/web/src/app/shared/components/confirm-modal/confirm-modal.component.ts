import { Component, input, output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfirmModalConfig } from './models/confirm-modal.model';

@Component({
  selector: 'app-confirm-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './confirm-modal.component.html',
})
export class ConfirmModalComponent {
  config = input.required<ConfirmModalConfig>();
  isOpen = input<boolean>(false);

  confirm = output<void>();
  close = output<void>();

  onConfirm(): void {
    this.confirm.emit();
  }

  onCancel(): void {
    this.close.emit();
  }
}
