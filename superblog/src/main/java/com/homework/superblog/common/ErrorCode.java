package com.homework.superblog.common;

public enum ErrorCode {

  SUCCESS(200),
  EXISTING_DATA(303),
  CREATED(201),
  NOT_FOUND_DATA(404);

  private final int code;

  ErrorCode(int code) {
      
    this.code = code;
  }

  public int getCode() {

    return code;
  }
}
