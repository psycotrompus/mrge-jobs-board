package com.mrge.jobs.service;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ApplicationState {

  PENDING, APPROVED, REJECTED

  ;

  @JsonValue
  public int toInt() {
    return ordinal();
  }

  public static ApplicationState from(Integer state) {
    var length = values().length;
    if (state == null || state < 0 || state > length) {
      return null;
    }
    return values()[state];
  }
}
