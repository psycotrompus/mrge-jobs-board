import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  notify = new Subject<any>();

  private es!: EventSource;

  connect() {
    if (this.es) {
      this.es.close();
    }
    this.es = new EventSource('http://localhost:8080/notifications');
    this.es.onmessage = (e: MessageEvent) => {
      this.notify.next(JSON.parse(e.data));
    };
    this.es.onerror = () => {
      console.error('Failed notification connection.');
      this.connect();
      this.notify.next(true);
    };
  }

  disconnect() {
    this.es.close();
  }
}
