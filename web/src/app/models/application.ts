export type Application = {
  id: number;
  jobId: number;
  name: string;
  email: string;
  message: string;
  submittedOn: Date,
  state: State;
};

export enum State {
  PENDING,
  APPROVED,
  REJECTED,
}
