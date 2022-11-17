package com.intellias.intellistart.interviewplanning.util.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Booking not found exception.
 */
public class BookingNotFoundException extends InterviewApplicationException {

  public BookingNotFoundException() {
    super("booking_not_found", HttpStatus.NOT_FOUND,
        "There is no such bookings in the database");
  }
}
