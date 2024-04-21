export type JobHeader = {
  id: number;
  jobId: number;
  name: string;
  office: string;
  url: string;
  descriptions: JobDescription[];
};

export type JobDescription = {
  id: number;
  name: string;
  description: string;
};

export type JobDetails = {
  id: number;
  jobId: number;
  subcompany: string;
  office: string;
  department: string;
  recruitingCategory: string;
  name: string;
  employmentType: string;
  seniority: string;
  schedule: string;
  yearsOfExperience: string;
  keywords: string;
  occupation: string;
  occupationCategory: string;
  descriptions: JobDescription[];
};
