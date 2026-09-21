import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-loading-screen',
  standalone: true,
  imports: [],
  styleUrl: './loading-screen.css',
  templateUrl: './loading-screen.html',
})
export class LoadingScreenComponent {
  @Input() isLoading: boolean = false;
  @Input() text: string = 'Procesando...';
}
