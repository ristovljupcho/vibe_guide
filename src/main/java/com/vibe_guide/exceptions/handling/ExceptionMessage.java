package com.vibe_guide.exceptions.handling;

import java.util.Map;
import lombok.Getter;

@Getter
public class ExceptionMessage {
  private Map<String, String> errorFields;
  private String generalExceptionMessage;

  public ExceptionMessage(Map<String, String> errorFields) {
    this.errorFields = errorFields;
  }

  public ExceptionMessage(String generalExceptionMessage) {
    this.generalExceptionMessage = generalExceptionMessage;
  }
}

