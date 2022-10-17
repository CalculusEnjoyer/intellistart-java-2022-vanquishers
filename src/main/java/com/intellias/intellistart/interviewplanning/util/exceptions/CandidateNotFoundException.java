package com.intellias.intellistart.interviewplanning.util.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Candidate not found exception.
 */
public class CandidateNotFoundException extends InterviewApplicationException {

  /**
   * Candidate not found exception constructor.
   */
  public CandidateNotFoundException() {
    super("candidate_not_found",
        HttpStatus.NOT_FOUND,
        "There is no candidate with such id in the database");
  }
}
