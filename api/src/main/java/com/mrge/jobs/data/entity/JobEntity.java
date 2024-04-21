package com.mrge.jobs.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("jobs")
public class JobEntity {

  @Id
  private Integer id;

  private Long jobId;

  private String subcompany;

  private String office;

  private String department;

  private String recruitingCategory;

  private String name;

  private String employmentType;

  private String seniority;

  private String schedule;

  private String yearsOfExperience;

  private String keywords;

  private String occupation;

  private String occupationCategory;
}
