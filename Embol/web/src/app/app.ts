import { Component, signal, inject, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MsalBroadcastService, MsalService } from '@azure/msal-angular';
import { EventMessage, EventType } from '@azure/msal-browser';
import { filter } from 'rxjs/operators';
@Component({
  imports: [RouterOutlet],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App implements OnInit {
  protected readonly title = signal('frontend-angular');
  
  private msalBroadcastService = inject(MsalBroadcastService);
  private msalService = inject(MsalService);

  ngOnInit(): void {
    // Escuchamos los eventos de MSAL a nivel global
    this.msalBroadcastService.msalSubject$
      .pipe(
        filter((msg: EventMessage) => msg.eventType === EventType.LOGIN_SUCCESS)
      )
      .subscribe((result: EventMessage) => {
        const payload = result.payload as any;
        if (payload && payload.account) {
          // Marcamos la cuenta como activa ni bien el login es exitoso
          this.msalService.instance.setActiveAccount(payload.account);
        }
      });
  }
}
