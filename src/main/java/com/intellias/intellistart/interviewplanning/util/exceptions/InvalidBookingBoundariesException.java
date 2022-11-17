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
        "Booking duration have to be 1.5 hours and it have to be registered in future.");
  }
}
