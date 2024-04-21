package com.mrge.jobs.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class JobUpdateSchedule {

  private final JobUpdate jobUpdate;

  private String url;

  @Value("${jobs.url}")
  public void setUrl(String url) {
    this.url = url;
  }

  @PostConstruct
  public void start() {
    execute();
  }

  @Scheduled(cron = "0 0 12 ? * *")
  public void execute() {
    jobUpdate.run(url).subscribe(
        count -> log.info("Successfully fetch [{}] job postings.", count),
        ex -> log.error("Failed to fetch job postings.", ex),
        () -> log.info("Finished fetching job postings.")
    );
  }
}
