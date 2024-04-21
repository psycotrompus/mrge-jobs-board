package com.mrge.jobs.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("descriptions")
public class DescriptionEntity {

  @Id
  private Integer id;

  private Integer jobId;

  private String name;

  private String description;
}
