export type JobHeader = {
  id: number;
  jobId: number;
  name: string;
  office: string;
  url: string;
  descriptions: JobDescription[];
}

export type JobDescription = {
  id: number;
  name: string;
  description: string;
}
