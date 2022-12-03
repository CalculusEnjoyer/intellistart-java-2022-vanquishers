package com.intellias.intellistart.interviewplanning.util.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Invalid week num exception, usually thrown when validation of the week num fails.
 */
public class InvalidWeekNumException extends InterviewApplicationException {

  /**
   * Constructor for invalid week num exception.
   *
   * @param message exception message
   */
  public InvalidWeekNumException(String message) {
    super("invalid_weeknum", HttpStatus.BAD_REQUEST, message);
  }
}
