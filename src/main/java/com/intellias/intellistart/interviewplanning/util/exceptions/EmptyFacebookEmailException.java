package com.intellias.intellistart.interviewplanning.util.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Empty Facebook Email exception.
 */
public class EmptyFacebookEmailException extends InterviewApplicationException {

  /**
   * Empty Facebook Email Exception basic constructor.
   */
  public EmptyFacebookEmailException() {
    super("empty_email_exception", HttpStatus.FORBIDDEN,
        "Empty Email field, can not register User");
  }
}
