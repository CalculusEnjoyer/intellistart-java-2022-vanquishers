package com.intellias.intellistart.interviewplanning.util.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Interviewer not found exception.
 */
public class InterviewerNotFoundException extends InterviewApplicationException {

  /**
   * Interviewer not found constructor.
   */
  public InterviewerNotFoundException() {
    super("interviewer_not_found",
        HttpStatus.NOT_FOUND,
        "There is no interviewer with such id in the database");
  }
}
