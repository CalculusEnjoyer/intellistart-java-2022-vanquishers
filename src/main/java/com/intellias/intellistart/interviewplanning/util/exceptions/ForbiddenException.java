package com.intellias.intellistart.interviewplanning.util.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Forbidden HTTP Response exception.
 */
public class ForbiddenException extends InterviewApplicationException {

  /**
   * Constructor unauthorized access exception.
   *
   * @param message describing message about what lead to that exception
   */
  public ForbiddenException(String message) {
    super("forbidden", HttpStatus.FORBIDDEN, message);
  }
}
