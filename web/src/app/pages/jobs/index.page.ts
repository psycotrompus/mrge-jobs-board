import { Component, NgZone, OnInit } from "@angular/core";
import { JobHeader } from '../../models/job';
import CardComponent from '../../components/card/card.component';
import { NgFor } from '@angular/common';
import { MatButton } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import ApplicationDialog from './application.dialog';
import AlertComponent, { AlertData } from '../../components/alert/alert.component';
import { Application } from '../../models/application';

@Component({
  selector: 'app-jobs',
  standalone: true,
  templateUrl: './index.page.html',
  styleUrls: [ './index.page.scss' ],
  imports: [
    CardComponent,
    AlertComponent,
    NgFor,
    MatButton,
  ]
})
export default class JobsPage implements OnInit {

  jobs: JobHeader[] = [];

  constructor(
      private zone: NgZone,
      private dialog: MatDialog
  ) {}

  async ngOnInit(): Promise<void> {
    console.log('Fetching jobs list...');
    const res = await fetch('http://localhost:8080/jobs');
    const data = await res.json();
    this.zone.run(() => {
      this.jobs = data;
    });
  }

  apply(job: JobHeader): void {
    const dialogRef = this.dialog.open(ApplicationDialog, {
      data: { job },
      panelClass: 'app-form'
    });

    dialogRef.afterClosed().subscribe(async application => {
      if (application) {
        await this.sendApplication(application);
      }
    });
  }

  async sendApplication(application: Application) {
    const res = await fetch('http://localhost:8080/jobs/apply', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(application),
    });
    let alertData: AlertData = { title: '', message: '' };
    if (res.ok) {
      alertData.title = 'Success';
      alertData.message = 'Your application was successfully submitted.';
    }
    else if (res.status === 400) {
      alertData.title = 'Duplicate';
      alertData.message = 'You have already applied for this job.';
    }
    else {
      alertData.title = 'Failed';
      alertData.message = 'We\'re having some problem submitting your application. Please try again later.';
    }
    this.dialog.open(AlertComponent, {
      data: alertData
    });
  }
}
