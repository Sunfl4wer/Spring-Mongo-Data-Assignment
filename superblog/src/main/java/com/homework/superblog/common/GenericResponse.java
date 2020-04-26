package com.homework.superblog.common;

import lombok.Getter;
import lombok.Setter;

public class GenericResponse {

  @Getter
  @Setter
  private int errorCode;

  @Getter
  @Setter
  private Object data;

  public GenericResponse() {}

  public GenericResponse(int errorCode) {
    
    this.errorCode = errorCode;
  }

  public GenericResponse(int errorCode, Object data) {
    
    this(errorCode);
    this.data = data;
  }
}
