package com.mrge.jobs.rest;

import com.mrge.jobs.dto.JobDetailsDto;
import com.mrge.jobs.dto.JobHeaderDto;
import com.mrge.jobs.dto.SubmissionDto;
import com.mrge.jobs.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobsApi {

  private final JobService jobSvc;

  @GetMapping
  public Flux<JobHeaderDto> getJobs(ServerHttpRequest req) {
    return jobSvc.getJobs(req.getURI().toString());
  }

  @GetMapping("/{jobId}")
  public Mono<JobDetailsDto> getJob(@PathVariable Integer jobId) {
    return jobSvc.getJobDetails(jobId);
  }

  @PostMapping("/apply")
  public Mono<Void> apply(@RequestBody SubmissionDto submission) {
    return jobSvc.apply(submission);
  }
}
