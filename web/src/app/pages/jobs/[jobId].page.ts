import { Component, Input, NgZone, OnInit, signal } from '@angular/core';
import { JobDetails } from '../../models/job';
import { NgForOf } from '@angular/common';

@Component({
  selector: 'app-job-details',
  standalone: true,
  templateUrl: './[jobId].page.html',
  imports: [
    NgForOf
  ],
  styleUrls: []
})
export default class DetailsPage implements OnInit {

  @Input() jobId!: string

  job = signal<JobDetails>({
    department: '',
    descriptions: [],
    employmentType: '',
    id: 0,
    jobId: 0,
    keywords: '',
    name: '',
    occupation: '',
    occupationCategory: '',
    office: '',
    recruitingCategory: '',
    schedule: '',
    seniority: '',
    subcompany: '',
    yearsOfExperience: ''
  });

  constructor(private zone: NgZone) {}

  async ngOnInit(): Promise<void> {
    const res = await fetch(`http://localhost:8080/jobs/${this.jobId}`);
    const job = await res.json();
    this.zone.run(() => {
      this.job.set(job);
    });
  }

}
