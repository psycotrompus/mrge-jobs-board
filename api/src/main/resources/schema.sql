CREATE TABLE jobs (
  id INT PRIMARY KEY AUTO_INCREMENT,
  job_id LONG NOT NULL,
  subcompany VARCHAR(100) NULL,
  office VARCHAR(100) NULL,
  department VARCHAR(100) NULL,
  recruiting_category VARCHAR(100) NULL,
  name VARCHAR(255) NOT NULL,
  employment_type VARCHAR(100) NULL,
  seniority VARCHAR(100) NULL,
  schedule VARCHAR(100) NULL,
  years_of_experience VARCHAR(20) NULL,
  keywords VARCHAR(255) NULL,
  occupation VARCHAR(100) NULL,
  occupation_category VARCHAR(100) NULL
);

CREATE TABLE descriptions (
  id INT PRIMARY KEY AUTO_INCREMENT,
  job_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  description TEXT NOT NULL
);

CREATE TABLE applications (
  id INT PRIMARY KEY AUTO_INCREMENT,
  job_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(200) NOT NULL,
  message TEXT NOT NULL,
  submitted_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  state INT NOT NULL
);

ALTER TABLE descriptions ADD FOREIGN KEY (job_id) REFERENCES jobs(id);

ALTER TABLE applications ADD FOREIGN KEY (job_id) REFERENCES jobs(id);
