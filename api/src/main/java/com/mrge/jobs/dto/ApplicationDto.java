package com.mrge.jobs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mrge.jobs.service.ApplicationState;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class ApplicationDto {

  Integer id;

  Integer jobId;

  String name;

  String email;

  String message;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "uuuu-MM-dd'T'HH:mm:ss")
  LocalDateTime submittedOn;

  ApplicationState state;
}
