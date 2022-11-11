package com.intellias.intellistart.interviewplanning.util.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Interviewer not found exception.
 */
public class ExcessBookingLimitException extends InterviewApplicationException {

  /**
   * Interviewer not found constructor.
   */
  public ExcessBookingLimitException() {
    super("excess_booking_limit",
        HttpStatus.BAD_REQUEST,
        "The number of already created slots equals or exceeds the booking limit");
  }
}
