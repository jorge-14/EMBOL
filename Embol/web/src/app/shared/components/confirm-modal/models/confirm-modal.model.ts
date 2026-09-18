export interface ConfirmModalConfig {
  title: string;
  description: string;
  confirmLabel?: string;
  cancelLabel?: string;
  icon?: 'warning' | 'danger' | 'info' | 'question';
}
