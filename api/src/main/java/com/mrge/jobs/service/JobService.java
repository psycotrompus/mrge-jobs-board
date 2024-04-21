package com.mrge.jobs.service;

import com.mrge.jobs.dto.ApplicationDto;
import com.mrge.jobs.dto.JobDetailsDto;
import com.mrge.jobs.dto.JobHeaderDto;
import com.mrge.jobs.dto.SubmissionDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface JobService {

  Flux<JobHeaderDto> getJobs();

  Mono<JobDetailsDto> getJobDetails(Integer id);

  Mono<Void> apply(SubmissionDto submission);

  Flux<ApplicationDto> getApplications();

  Mono<Void> confirm(Integer appId, Integer state);
}
