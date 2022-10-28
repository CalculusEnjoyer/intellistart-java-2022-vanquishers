package com.intellias.intellistart.interviewplanning.util.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Slot not found exception.
 */
public class InterviewerSlotNotFoundException extends InterviewApplicationException {

  /**
   * Interviewer slot not found exception constructor.
   */
  public InterviewerSlotNotFoundException() {

    super("interviewer_slot_not_found",
        HttpStatus.NOT_FOUND,
        "There is no interviewer slot with such id in the database");
  }
}
