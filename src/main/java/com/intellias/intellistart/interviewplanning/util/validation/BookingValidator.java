package com.intellias.intellistart.interviewplanning.util.validation;

import com.intellias.intellistart.interviewplanning.models.Booking;
import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.services.WeekService;
import com.intellias.intellistart.interviewplanning.util.exceptions.BookingOutOfSlotException;
import com.intellias.intellistart.interviewplanning.util.exceptions.OverlappingBookingException;
import java.util.Set;

/**
 * Validator for Bookings.
 */
public class BookingValidator {

  private BookingValidator() {
  }

  /**
   * Validates that Booking lays in CandidateSlot range.
   */
  public static void isInCandidateSlotRange(CandidateSlot slot, Booking booking) {
    if (slot.getDateFrom().compareTo(booking.getFrom()) > 0
        || slot.getDateTo().compareTo(booking.getTo()) < 0) {
      throw new BookingOutOfSlotException();
    }
  }

  /**
   * Validates that BookingDto lays in InterviewerSlot range.
   */
  public static void isInInterviewerSlotRange(InterviewerSlot slot, Booking booking) {
    if (slot.getWeekNum() != WeekService.getWeekNumFrom(booking.getFrom().toLocalDate())
        || slot.getDayOfWeek() != WeekService.getDayOfWeekFrom(booking.getFrom().toLocalDate())
        || slot.getWeekNum() != WeekService.getWeekNumFrom(booking.getTo().toLocalDate())
        || slot.getDayOfWeek() != WeekService.getDayOfWeekFrom(booking.getTo().toLocalDate())
        || slot.getFrom().compareTo(booking.getFrom().toLocalTime()) > 0
        || slot.getTo().compareTo(booking.getTo().toLocalTime()) < 0) {
      throw new BookingOutOfSlotException();
    }
  }

  /**
   * Checks for overlapping with bookins in set.
   */
  public static void isOverLappingWithBookings(Set<Booking> bookings, Booking booking) {
    for (Booking bookingToCheck : bookings) {
      if (UtilValidator.areIntervalsOverLapping(bookingToCheck.getFrom(), bookingToCheck.getTo(),
          booking.getFrom(), booking.getTo())) {
        throw new OverlappingBookingException();
      }
    }
  }
}