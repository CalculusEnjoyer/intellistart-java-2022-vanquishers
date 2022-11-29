package com.intellias.intellistart.interviewplanning.util.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception for invalid slot access.
 */
public class SlotAccessException extends InterviewApplicationException {

  /**
   * Constructor for basic exception.
   */
  public SlotAccessException() {
    super("slot_access_error",
        HttpStatus.BAD_REQUEST,
        "This slot belongs to another user");
  }
}
