package com.intellias.intellistart.interviewplanning.util.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Overlapping slot exception.
 */
public class OverlappingSlotException extends InterviewApplicationException {

  /**
   * Overlapping slot exception constructor.
   */
  public OverlappingSlotException() {
    super("slot_is_overlapping",
        HttpStatus.BAD_REQUEST,
        "This slot can not match with already existing slots.");
  }
}
