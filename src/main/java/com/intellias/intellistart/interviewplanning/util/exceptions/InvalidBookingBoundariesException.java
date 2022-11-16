package com.intellias.intellistart.interviewplanning.util.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Invalid slot boundaries exception.
 */
public class InvalidBookingBoundariesException extends InterviewApplicationException {

  /**
   * Invalid slot boundaries exception default constructor.
   */
  public InvalidBookingBoundariesException() {
    super("invalid_booking_boundaries",
        HttpStatus.BAD_REQUEST,
        "Booking duration have to be rounded to 30 minutes and be greater than 1.5 hours.");
  }
}
