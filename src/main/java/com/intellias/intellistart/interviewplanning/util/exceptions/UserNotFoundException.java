package com.intellias.intellistart.interviewplanning.util.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends InterviewApplicationException{

  /**
   * Can slot not found exception constructor.
   */
  public UserNotFoundException() {

    super("user_not_found",
        HttpStatus.NOT_FOUND,
        "There is no user with such id in the database");
  }
}
