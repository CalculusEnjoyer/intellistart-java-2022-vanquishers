package com.intellias.intellistart.interviewplanning.services;

import static java.util.Calendar.YEAR;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.intellias.intellistart.interviewplanning.models.Booking;
import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.models.enums.Status;
import com.intellias.intellistart.interviewplanning.util.exceptions.BookingOutOfSlotException;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingValidatorTest {

  @Test
  @Order(1)
  void validateBookingOverlappingWithCandidateSlotTest() {
    CandidateSlot candidateSlot = new CandidateSlot(
        LocalDateTime.of(LocalDate.of(YEAR, Month.OCTOBER, 12), LocalTime.of(9, 30)),
        LocalDateTime.of(LocalDate.of(YEAR, Month.OCTOBER, 12), LocalTime.of(11, 0)));

    Booking notOutOfBoundaries = new Booking(
        LocalDateTime.of(LocalDate.of(YEAR, Month.OCTOBER, 12), LocalTime.of(9, 30)),
        LocalDateTime.of(YEAR,
            Month.OCTOBER, 12, 10, 00), "check", "check", Status.BOOKED);

    Booking outOfBoundaries = new Booking(
        LocalDateTime.of(LocalDate.of(YEAR, Month.OCTOBER, 12), LocalTime.of(9, 30)),
        LocalDateTime.of(YEAR,
            Month.OCTOBER, 12, 12, 00), "check", "check", Status.BOOKED);

    assertThrows(BookingOutOfSlotException.class,
        () -> BookingValidator.isInCandidateSlotRange(
            candidateSlot,
            outOfBoundaries));
    assertDoesNotThrow(
        () -> BookingValidator.isInCandidateSlotRange(
            candidateSlot,
            notOutOfBoundaries));
  }

  @Test
  @Order(2)
  void validateBookingOutOfBoundariesWithInterviewerSlotTest() {
    InterviewerSlot interviewerSlot = new InterviewerSlot(202201, 1,
        LocalTime.of(9, 30), LocalTime.of(11, 0));

    Booking notOutOfBoundaries = new Booking(
        LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 3), LocalTime.of(9, 30)),
        LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 3), LocalTime.of(11, 0)), "check",
        "check", Status.BOOKED);

    Booking outOfBoundaries1 = new Booking(
        LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 3), LocalTime.of(11, 30)),
        LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 3), LocalTime.of(12, 30)), "check",
        "check", Status.BOOKED);

    Booking outOfBoundaries2 = new Booking(
        LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 4), LocalTime.of(9, 30)),
        LocalDateTime.of(LocalDate.of(2022, Month.JANUARY, 4), LocalTime.of(12, 30)), "check",
        "check", Status.BOOKED);

    assertThrows(BookingOutOfSlotException.class,
        () -> BookingValidator.isInInterviewerSlotRange(
            interviewerSlot,
            outOfBoundaries1));
    assertDoesNotThrow(
        () -> BookingValidator.isInInterviewerSlotRange(
            interviewerSlot,
            notOutOfBoundaries));
    assertThrows(BookingOutOfSlotException.class,
        () -> BookingValidator.isInInterviewerSlotRange(
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
        () -> BookingValidator.isOverLappingWithBookings(bookings, overLapping));
    assertThrows(OverlappingBookingException.class,
        () -> BookingValidator.isOverLappingWithBookings(bookings, overLapping1));
    assertDoesNotThrow(() -> BookingValidator.isOverLappingWithBookings(bookings, notOverlap));
    assertDoesNotThrow(() -> BookingValidator.isOverLappingWithBookings(bookings, notOverlap1));
    assertDoesNotThrow(() -> BookingValidator.isOverLappingWithBookings(bookings, notOverlap2));
  }
}
