package com.mrge.jobs.rest;

import com.mrge.jobs.dto.ApplicationDto;
import com.mrge.jobs.service.SubmittedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Slf4j
@RestController
@RequestMapping("/notifications")
public class NotificationApi {

  private final Sinks.Many<ApplicationDto> applications = Sinks.many().multicast().directBestEffort();

  @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ApplicationDto> applications() {
    return applications.asFlux()
        .doOnNext(app -> log.info("Sending application: {}", app))
        .log();
  }

  @EventListener
  public void handle(SubmittedEvent evt) {
    var app = evt.getApplication();
    log.debug("Received application: {}", app);
    applications.tryEmitNext(app);
  }

}
