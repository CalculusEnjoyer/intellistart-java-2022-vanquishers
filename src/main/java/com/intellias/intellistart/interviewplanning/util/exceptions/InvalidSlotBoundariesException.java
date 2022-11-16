package com.intellias.intellistart.interviewplanning.util.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Invalid slot boundaries exception.
 */
public class InvalidSlotBoundariesException extends InterviewApplicationException {

  /**
   * Invalid slot boundaries exception default constructor.
   */
  public InvalidSlotBoundariesException() {
    super("invalid_boundaries",
        HttpStatus.BAD_REQUEST,
        "Slot duration have to be rounded to 30 minutes.");
  }

  /**
   * Invalid slot boundaries exception constructor with provided message.
   */
  public InvalidSlotBoundariesException(String message) {
    super("invalid_boundaries",
        HttpStatus.BAD_REQUEST,
        message);
  }
}
