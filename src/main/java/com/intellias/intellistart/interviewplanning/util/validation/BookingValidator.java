package com.intellias.intellistart.interviewplanning.util.validation;

import com.intellias.intellistart.interviewplanning.controllers.dto.BookingDto;
import com.intellias.intellistart.interviewplanning.models.Booking;
import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.services.WeekService;
import com.intellias.intellistart.interviewplanning.util.exceptions.BookingOutOfSlotException;
import com.intellias.intellistart.interviewplanning.util.exceptions.InvalidBookingBoundariesException;
import com.intellias.intellistart.interviewplanning.util.exceptions.OverlappingBookingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Validator for Bookings.
 */
@Component
public class BookingValidator {

  private final WeekService weekService;

  /**
   * Private constructor to hide the ability of instantiation of a utility class.
   */
  @Autowired
  private BookingValidator(WeekService weekService) {
    this.weekService = weekService;
  }

  /**
   * Validates that Booking lays in CandidateSlot range.
   */
  public void isInSlotRange(CandidateSlot slot, Booking booking) {
    if (slot.getDateFrom().compareTo(booking.getFrom()) > 0
        || slot.getDateTo().compareTo(booking.getTo()) < 0) {
      throw new BookingOutOfSlotException();
    }
  }

  /**
   * Validates that BookingDto lays in InterviewerSlot range.
   */
  public void isInSlotRange(InterviewerSlot slot, Booking booking) {
    if (slot.getWeekNum() != weekService.getWeekNumFrom(booking.getFrom().toLocalDate())
        || slot.getDayOfWeek() != weekService.getDayOfWeekFrom(booking.getFrom().toLocalDate())
        || slot.getWeekNum() != weekService.getWeekNumFrom(booking.getTo().toLocalDate())
        || slot.getDayOfWeek() != weekService.getDayOfWeekFrom(booking.getTo().toLocalDate())
        || slot.getFrom().compareTo(booking.getFrom().toLocalTime()) > 0
        || slot.getTo().compareTo(booking.getTo().toLocalTime()) < 0) {
      throw new BookingOutOfSlotException();
    }
  }

  /**
   * Checks for overlapping with bookings in set.
   */
  public void isOverLappingWithBookings(Set<Booking> bookings, Booking booking) {
    for (Booking bookingToCheck : bookings) {
      if (UtilValidator.areIntervalsOverLapping(bookingToCheck.getFrom(), bookingToCheck.getTo(),
          booking.getFrom(), booking.getTo())) {
        throw new OverlappingBookingException();
      }
    }
  }

  /**
   * Method for validating booking boundaries.
   */
  public boolean isValidBookingTimeBoundaries(BookingDto dto) {
    LocalDateTime fromTime = dto.getDateFrom();
    LocalDateTime toTime = dto.getDateTo();
    return dto.getDateFrom().isAfter(LocalDateTime.now(weekService.getZoneId()))
        && Duration.between(fromTime, toTime).toMinutes() == 90;
  }

  /**
   * Method that throws exception if booking has invalid boundaries.
   */
  public BookingDto validDtoBoundariesOrError(BookingDto dto) {
    if (!isValidBookingTimeBoundaries(dto)) {
      throw new InvalidBookingBoundariesException();
    }
    return dto;
  }

}
