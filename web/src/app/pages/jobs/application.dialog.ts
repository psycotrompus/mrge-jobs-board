import { Component, Inject } from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef, MatDialogTitle
} from '@angular/material/dialog';
import { JobHeader } from '../../models/job';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { Application, State } from '../../models/application';
import { FormsModule } from '@angular/forms';
import { MatInput } from '@angular/material/input';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-job-form',
  standalone: true,
  templateUrl: './application.dialog.html',
  styleUrls: [ './application.dialog.scss'],
  imports: [
    MatDialogContent,
    MatFormField,
    MatDialogActions,
    MatDialogClose,
    FormsModule,
    MatInput,
    MatLabel,
    MatButton,
    MatDialogTitle
  ],
})
export default class ApplicationDialog {

  job: JobHeader;

  application: Application = {
    id: 0,
    jobId: 0,
    name: '',
    email: '',
    message: '',
    submittedOn: new Date(),
    state: State.PENDING
  };

  constructor(
      private dialogRef: MatDialogRef<ApplicationDialog>,
      @Inject(MAT_DIALOG_DATA) private data: { job: JobHeader }
  ) {
    this.job = data.job;
  }

  close() {
    this.dialogRef.close();
  }

  submit() {
    this.application.jobId = this.job.id;
    this.application.submittedOn = new Date();
    this.dialogRef.close(this.application);
  }
}
