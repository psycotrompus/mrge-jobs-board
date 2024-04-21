package com.mrge.jobs.dto;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class JobHeaderDto {

  Integer id;

  Long jobId;

  String name;

  String office;

  String url;

  @Singular
  List<DescriptionDto> descriptions;
}
