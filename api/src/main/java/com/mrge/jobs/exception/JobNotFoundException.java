package com.mrge.jobs.exception;

public class JobNotFoundException extends RuntimeException {

  public JobNotFoundException(String message) {
    super(message);
  }

  public JobNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
