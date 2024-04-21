package com.mrge.jobs.service;

import com.mrge.jobs.dto.ApplicationDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SubmittedEvent extends ApplicationEvent {

  private final ApplicationDto application;

  SubmittedEvent(Object source, ApplicationDto application) {
    super(source);
    this.application = application;
  }
}
