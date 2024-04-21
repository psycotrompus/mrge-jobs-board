package com.mrge.jobs.rest;

import com.mrge.jobs.exception.JobApplicationException;
import com.mrge.jobs.exception.JobNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(JobNotFoundException.class)
  public ResponseEntity<String> handleJobNotFound() {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler
  public ResponseEntity<String> handleApplication(JobApplicationException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGeneric() {
    return ResponseEntity.internalServerError().build();
  }
}
