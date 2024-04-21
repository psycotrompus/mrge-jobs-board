package com.mrge.jobs.exception;

public class JobUpdateException extends RuntimeException {

  public JobUpdateException(String message) {
    super(message);
  }

  public JobUpdateException(String message, Throwable cause) {
    super(message, cause);
  }
}
