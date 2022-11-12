package com.intellias.intellistart.interviewplanning.util.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Booking not found exception.
 */
public class BookingOutOfSlotException extends InterviewApplicationException {

  public BookingOutOfSlotException() {
    super("booking_overlapping", HttpStatus.NOT_FOUND,
        "This booking is out of slot boundaries.");
  }
}
