package com.mrge.jobs;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class JobsApiApplication {

  @Bean
  public Module javaTimeModule() {
    return new JavaTimeModule();
  }

  public static void main(String[] args) {
    SpringApplication.run(JobsApiApplication.class, args);
  }

}
