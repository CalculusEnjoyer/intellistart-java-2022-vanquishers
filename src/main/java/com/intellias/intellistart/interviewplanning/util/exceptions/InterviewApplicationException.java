package com.intellias.intellistart.interviewplanning.util.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Basic exception for the application.
 */
@Getter
@Setter
public class InterviewApplicationException extends RuntimeException {

  @JsonIgnore
  protected final HttpStatus httpStatus;
  protected final String errorCode;
  protected final String errorMessage;

  /**
   * Constructor for basic exception.
   */
  public InterviewApplicationException(String errorCode, HttpStatus httpStatus, String message) {
    this.errorMessage = message;
    this.httpStatus = httpStatus;
    this.errorCode = errorCode;
  }
}
