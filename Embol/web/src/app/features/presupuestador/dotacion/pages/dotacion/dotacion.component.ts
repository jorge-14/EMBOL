import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { DotacionService } from '../../services/dotacion.service';
import { GestionDotacion } from '../../models/dotacion.model';
import { CheckboxModule } from 'primeng/checkbox';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-dotacion',
  standalone: true,
  imports: [CommonModule, CheckboxModule, FormsModule],
  templateUrl: './dotacion.component.html',
  styleUrl: './dotacion.component.scss'
})
export class DotacionComponent implements OnInit {
  gestiones = signal<GestionDotacion[]>([]);
  loading = signal(false);

  constructor(
    private dotacionService: DotacionService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadGestiones();
  }

  loadGestiones(): void {
    this.loading.set(true);
    this.dotacionService.getGestiones().subscribe({
      next: (data) => {
        this.gestiones.set(data);
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Error cargando gestiones', err);
        this.loading.set(false);
      }
    });
  }

  onAbrir(gestion: GestionDotacion): void {
    this.router.navigate(['/presupuestador/dotacion', gestion.id]);
  }
}
