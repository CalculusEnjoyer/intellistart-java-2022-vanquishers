package com.intellias.intellistart.interviewplanning.util.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Slot not found exception.
 */
public class CandidateSlotNotFoundException extends InterviewApplicationException {

  /**
   * Can slot not found exception constructor.
   */
  public CandidateSlotNotFoundException() {

    super("candidate_slot_not_found",
        HttpStatus.NOT_FOUND,
        "There is no candidate slot with such id in the database");
  }
}
