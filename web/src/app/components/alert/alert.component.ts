import { Component, Inject, NgZone, signal } from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from '@angular/material/dialog';
import { MatButton } from '@angular/material/button';

export type AlertData = {
  title: string;
  message: string;
};

@Component({
  selector: 'app-alert',
  standalone: true,
  templateUrl: './alert.component.html',
  imports: [
    MatButton,
    MatDialogActions,
    MatDialogContent,
    MatDialogTitle,
  ],
  styleUrls: []
})
export default class AlertComponent {

  title = signal('');

  message = signal('');

  constructor(
      private zone: NgZone,
      private dialogRef: MatDialogRef<AlertComponent>,
      @Inject(MAT_DIALOG_DATA) private data: AlertData
  ) {
    console.log('setting alert data...');
    setTimeout(() => {
      zone.run(() => {
        this.title.set(this.data.title);
        this.message.set(this.data.message);
      });
    });
  }

  dismiss() {
    this.dialogRef.close();
  }
}
