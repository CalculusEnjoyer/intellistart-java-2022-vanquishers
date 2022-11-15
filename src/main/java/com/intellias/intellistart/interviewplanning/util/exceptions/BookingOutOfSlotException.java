package com.intellias.intellistart.interviewplanning.util.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Booking out of slot exception.
 */
public class BookingOutOfSlotException extends InterviewApplicationException {

  public BookingOutOfSlotException() {
    super("booking_out_of_slot", HttpStatus.BAD_REQUEST,
        "This booking is out of slot boundaries.");
  }
}
