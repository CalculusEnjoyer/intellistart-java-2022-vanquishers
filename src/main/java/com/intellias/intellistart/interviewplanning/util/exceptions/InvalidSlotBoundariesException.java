package com.intellias.intellistart.interviewplanning.util.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Invalid slot boundaries exception.
 */
public class InvalidSlotBoundariesException extends InterviewApplicationException {

  /**
   * Invalid slot boundaries exception constructor.
   */
  public InvalidSlotBoundariesException() {
    super("invalid_boundaries",
        HttpStatus.BAD_REQUEST,
        "Slot duration have to be rounded to 30 minutes");
  }
}
