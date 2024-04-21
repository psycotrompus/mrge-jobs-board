import { Component, NgZone, OnInit, signal } from '@angular/core';
import { NgFor, NgIf } from '@angular/common';
import { Application, State } from '../../models/application';
import { NotificationService } from '../../services/notification';
import CardComponent from '../../components/card/card.component';

@Component({
  selector: 'app-moderator',
  standalone: true,
  templateUrl: './index.page.html',
  styleUrls: [],
  imports: [ NgFor, CardComponent, NgIf ]
})
export default class ModeratorPage implements OnInit {

  applications = signal<Application[]>([]);

  constructor(
      private zone: NgZone,
      private notifSvc: NotificationService
  ) {}

  async ngOnInit() {
    this.notifSvc.connect();
    await this.refresh();
    this.notifSvc.notify.subscribe({
      next: async () => {
        console.log('refreshing list...');
        await this.refresh();
      }
    });
  }

  async refresh() {
    const apps = await fetch('http://localhost:8080/moderator/applications')
      .then(res => res.json())
    console.log(apps);
    this.zone.run(() => {
      this.applications.set(apps);
    });
  }

  async confirm(appId: number, state: State) : Promise<void> {
    await fetch(`http://localhost:8080/moderator/confirm/${appId}/${state}`, {
      method: 'POST'
    });
    await this.refresh();
  }

  protected readonly State = State;
}
