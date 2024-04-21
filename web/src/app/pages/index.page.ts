import { Component, OnInit, signal } from '@angular/core';
import { NotificationService } from '../services/notification';

@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './index.page.html',
  styleUrls: [ './index.page.scss' ],
})
export default class HomeComponent implements OnInit {

  count = signal(0);

  constructor(private notifSvc: NotificationService) {}

  ngOnInit(): void {
    this.notifSvc.connect();
  }

  goto(url: string) {
    window.location.href = url;
  }

  increment() {
    this.count.update(c => c + 1);
  }
}
