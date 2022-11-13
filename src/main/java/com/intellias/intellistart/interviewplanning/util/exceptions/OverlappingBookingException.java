package com.intellias.intellistart.interviewplanning.util.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Overlapping slot exception.
 */
public class OverlappingBookingException extends InterviewApplicationException {

  /**
   * Overlapping slot exception constructor.
   */
  public OverlappingBookingException() {
    super("booking_is_overlapping",
        HttpStatus.BAD_REQUEST,
        "This booking can not match with already existing bookings.");
  }
}
