package com.intellias.intellistart.interviewplanning.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Slot not found exception.
 */
public class SlotNotFoundException extends InterviewApplicationException {

  /**
   * Slot not found exception constructor.
   */
  public SlotNotFoundException() {
    super("slot_not_found",
        HttpStatus.NOT_FOUND,
        "There is no slot with such id in the database");
  }
}
