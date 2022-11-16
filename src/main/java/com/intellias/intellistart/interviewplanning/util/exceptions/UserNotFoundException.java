package com.intellias.intellistart.interviewplanning.util.exceptions;

import org.springframework.http.HttpStatus;

/**
 * User not found exception.
 */
public class UserNotFoundException extends InterviewApplicationException {

  /**
   * User not found exception constructor.
   */
  public UserNotFoundException() {

    super("user_not_found",
        HttpStatus.NOT_FOUND,
        "There is no such user in the database.");
  }
}
