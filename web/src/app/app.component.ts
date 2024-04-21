import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NotificationService } from './services/notification';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet
  ],
  providers: [
    NotificationService,
  ],
  template: ` <router-outlet></router-outlet> `,
  styles: [
    `
      :host {
        max-width: 1280px;
        margin: 0 auto;
        padding: 2rem;
      }
    `,
  ],
})
export class AppComponent {}
