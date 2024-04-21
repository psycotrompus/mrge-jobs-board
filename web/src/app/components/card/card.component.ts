import { Component, Input } from '@angular/core';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-card',
  standalone: true,
  templateUrl: './card.component.html',
  imports: [
    NgIf
  ],
  styleUrls: []
})
export default class CardComponent {

  @Input() title!: string;

  @Input() url?: string | null;

  @Input() subtitle?: string | null;

  @Input() message?: string | null;
}
