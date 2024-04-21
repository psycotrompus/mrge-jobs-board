package com.mrge.jobs.dto;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class JobDetailsDto {

  Integer id;
  Long jobId;
  String subcompany;
  String office;
  String department;
  String recruitingCategory;
  String name;
  String employmentType;
  String seniority;
  String schedule;
  String yearsOfExperience;
  String keywords;
  String occupation;
  String occupationCategory;
  @Singular
  List<DescriptionDto> descriptions;
}
