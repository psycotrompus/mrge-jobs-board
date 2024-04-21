package com.mrge.jobs.dto;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class JobXmlDto {

  Long jobId;
  String subcompany;
  String office;
  String department;
  String recruitingCategory;
  String name;
  @Singular
  Map<String, String> jobDescriptions;
  String employmentType;
  String seniority;
  String schedule;
  String yearsOfExperience;
  String keywords;
  String occupation;
  String occupationCategory;
}
