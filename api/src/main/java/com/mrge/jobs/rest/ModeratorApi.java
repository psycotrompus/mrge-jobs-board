package com.mrge.jobs.rest;

import com.mrge.jobs.dto.ApplicationDto;
import com.mrge.jobs.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/moderator")
@RequiredArgsConstructor
public class ModeratorApi {

  private final JobService jobSvc;

  @GetMapping("/applications")
  public Flux<ApplicationDto> getApplications() {
    return jobSvc.getApplications();
  }

  @PostMapping("/confirm/{appId}/{state}")
  public Mono<Void> confirm(
      @PathVariable Integer appId,
      @PathVariable Integer state
  ) {
    return jobSvc.confirm(appId, state);
  }
}
