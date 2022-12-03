package com.intellias.intellistart.interviewplanning.services;

import static java.util.Calendar.YEAR;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.intellias.intellistart.interviewplanning.controllers.dto.BookingDto;
import com.intellias.intellistart.interviewplanning.models.Booking;
import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.models.enums.Status;
import com.intellias.intellistart.interviewplanning.util.exceptions.BookingOutOfSlotException;
import com.intellias.intellistart.interviewplanning.util.exceptions.InvalidBookingBoundariesException;
import com.intellias.intellistart.interviewplanning.util.exceptions.OverlappingBookingException;
import com.intellias.intellistart.interviewplanning.util.validation.BookingValidator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Set;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingValidatorTest {

  @Autowired
  private BookingValidator bookingValidator;

  @Test
  @Order(1)
  void validateBookingOverlappingWithCandidateSlotTest() {
    CandidateSlot candidateSlot = new CandidateSlot(
        LocalDateTime.of(LocalDate.of(YEAR, Month.OCTOBER, 12), LocalTime.of(9, 30)),
        LocalDateTime.of(LocalDate.of(YEAR, Month.OCTOBER, 12), LocalTime.of(11, 0)));

    Booking notOutOfBoundaries = new Booking(
        LocalDateTime.of(LocalDate.of(YEAR, Month.OCTOBER, 12), LocalTime.of(9, 30)),
        LocalDateTime.of(YEAR,
            Month.OCTOBER, 12, 10, 0), "check", "check", Status.BOOKED);

    Booking outOfBoundaries = new Booking(
        LocalDateTime.of(LocalDate.of(YEAR, Month.OCTOBER, 12), LocalTime.of(9, 30)),
        LocalDateTime.of(YEAR,
            Month.OCTOBER, 12, 12, 0), "check", "check", Status.BOOKED);

    assertThrows(BookingOutOfSlotException.class,
        () -> bookingValidator.isInSlotRange(
            candidateSlot,
            outOfBoundaries));
    assertDoesNotThrow(
        () -> bookingValidator.isInSlotRange(
            candidateSlot,
            notOutOfBoundaries));
  }

  @Test
  @Order(2)
  void validateBookingOutOfBoundariesWithInterviewerSlotTest() {
    InterviewerSlot interviewerSlot = new InterviewerSlot(202201, 1,
        LocalTime.of(9, 30), LocalTime.of(11, 0));

    Booking outOfBoundaries1 = new Booking(
        LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 3), LocalTime.of(11, 30)),
        LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 3), LocalTime.of(12, 30)), "check",
        "check", Status.BOOKED);

    Booking outOfBoundaries2 = new Booking(
        LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 4), LocalTime.of(9, 30)),
        LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 4), LocalTime.of(12, 30)), "check",
        "check", Status.BOOKED);

    assertThrows(BookingOutOfSlotException.class,
        () -> bookingValidator.isInSlotRange(
            interviewerSlot,
            outOfBoundaries1));
    assertThrows(BookingOutOfSlotException.class,
        () -> bookingValidator.isInSlotRange(
            interviewerSlot,
            outOfBoundaries2));
  }

  @Test
  @Order(3)
  void validateBookingOverlappingTest() {
    Set<Booking> bookings = Set.of(new Booking(
            LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 3), LocalTime.of(9, 30)),
            LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 3), LocalTime.of(11, 0)), "check",
            "check", Status.BOOKED),

        new Booking(
            LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 3), LocalTime.of(14, 30)),
            LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 3), LocalTime.of(16, 30)), "check",
            "check", Status.BOOKED),

        new Booking(
            LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 4), LocalTime.of(9, 30)),
            LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 4), LocalTime.of(12, 30)), "check",
            "check", Status.BOOKED));

    Booking notOverlap = new Booking(
        LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 5), LocalTime.of(9, 30)),
        LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 5), LocalTime.of(12, 30)), "check",
        "check", Status.BOOKED);
    Booking notOverlap1 = new Booking(
        LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 4), LocalTime.of(12, 30)),
        LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 4), LocalTime.of(13, 30)), "check",
        "check", Status.BOOKED);
    Booking notOverlap2 = new Booking(
        LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 4), LocalTime.of(14, 30)),
        LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 4), LocalTime.of(15, 30)), "check",
        "check", Status.BOOKED);
    Booking overLapping = new Booking(
        LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 4), LocalTime.of(10, 30)),
        LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 4), LocalTime.of(15, 30)), "check",
        "check", Status.BOOKED);
    Booking overLapping1 = new Booking(
        LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 3), LocalTime.of(12, 30)),
        LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 3), LocalTime.of(15, 30)), "check",
        "check", Status.BOOKED);

    assertThrows(OverlappingBookingException.class,
        () -> bookingValidator.isOverLappingWithBookings(bookings, overLapping));
    assertThrows(OverlappingBookingException.class,
        () -> bookingValidator.isOverLappingWithBookings(bookings, overLapping1));
    assertDoesNotThrow(() -> bookingValidator.isOverLappingWithBookings(bookings, notOverlap));
    assertDoesNotThrow(() -> bookingValidator.isOverLappingWithBookings(bookings, notOverlap1));
    assertDoesNotThrow(() -> bookingValidator.isOverLappingWithBookings(bookings, notOverlap2));
  }

  @Test
  @Order(4)
  void isValidBookingBoundariesTest() {
    BookingDto bookingDtoValid1 = new BookingDto(
        LocalDateTime.of(LocalDate.of(2048, Month.OCTOBER, 12), LocalTime.of(9, 30)),
        LocalDateTime.of(2048,
            Month.OCTOBER, 12, 11, 0), "check", "check", Status.BOOKED, 1L, 1L);
    BookingDto bookingDtoValid2 = new BookingDto(
        LocalDateTime.of(LocalDate.of(2048, Month.OCTOBER, 12), LocalTime.of(16, 30)),
        LocalDateTime.of(2048,
            Month.OCTOBER, 12, 18, 0), "check", "check", Status.BOOKED, 1L, 1L);
    BookingDto bookingDtoInValid1 = new BookingDto(
        LocalDateTime.of(LocalDate.of(2048, Month.OCTOBER, 12), LocalTime.of(9, 30)),
        LocalDateTime.of(2048,
            Month.OCTOBER, 12, 10, 0), "check", "check", Status.BOOKED, 1L, 1L);
    BookingDto bookingDtoInValid2 = new BookingDto(
        LocalDateTime.of(LocalDate.of(2048, Month.OCTOBER, 12), LocalTime.of(9, 31)),
        LocalDateTime.of(2048,
            Month.OCTOBER, 12, 13, 0), "check", "check", Status.BOOKED, 1L, 1L);

    assertThrows(InvalidBookingBoundariesException.class,
        () -> bookingValidator.validDtoBoundariesOrError(bookingDtoInValid1));
    assertThrows(InvalidBookingBoundariesException.class,
        () -> bookingValidator.validDtoBoundariesOrError(bookingDtoInValid2));
    assertDoesNotThrow(() -> bookingValidator.validDtoBoundariesOrError(bookingDtoValid1));
    assertDoesNotThrow(() -> bookingValidator.validDtoBoundariesOrError(bookingDtoValid2));
  }
}
