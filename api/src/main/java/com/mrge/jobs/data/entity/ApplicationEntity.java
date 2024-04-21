package com.mrge.jobs.data.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@Table("applications")
public class ApplicationEntity {

  @Id
  private Integer id;

  private Integer jobId;

  private String name;

  private String email;

  private String message;

  private LocalDateTime submittedOn;

  private int state;
}
