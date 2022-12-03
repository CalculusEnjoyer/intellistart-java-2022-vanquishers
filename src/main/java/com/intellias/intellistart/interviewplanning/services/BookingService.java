package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.models.Booking;
import com.intellias.intellistart.interviewplanning.repositories.BookingRepository;
import com.intellias.intellistart.interviewplanning.util.exceptions.BookingNotFoundException;
import com.intellias.intellistart.interviewplanning.util.validation.BookingValidator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Booking entity service.
 */
@Service
@Transactional
public class BookingService {

  private final BookingRepository repository;
  private final InterviewerService interviewerService;
  private final CandidateService candidateService;
  private final BookingValidator bookingValidator;

  /**
   * BookingService constructor.
   */
  @Autowired
  public BookingService(BookingRepository repository, InterviewerService interviewerService,
      CandidateService candidateService, BookingValidator bookingValidator) {
    this.repository = repository;
    this.interviewerService = interviewerService;
    this.candidateService = candidateService;
    this.bookingValidator = bookingValidator;
  }

  /**
   * Method for finding bookings by id. If bookings is not found, throws BookingNotFoundException.
   *
   * @param id id of bookings
   * @return needed bookings
   */
  public Booking getBookingById(Long id) {
    Optional<Booking> tempBooking = repository.findById(id);
    if (tempBooking.isPresent()) {
      return tempBooking.get();
    } else {
      throw new BookingNotFoundException();
    }
  }

  public List<Booking> getAllBookings() {
    return repository.findAll();
  }

  /**
   * Method for deleting bookings by id.
   *
   * @param id id of bookings
   */
  public void deleteBookingById(Long id) {
    Booking booking = repository.findById(id).orElseThrow(BookingNotFoundException::new);
    booking.getInterviewerSlot().getBooking().remove(booking);
    booking.getCandidateSlot().getBooking().remove(booking);
    repository.deleteById(id);
  }

  /**
   * Method that register booking and checks if it overlaps with other bookings or slots.
   */
  public Booking registerBooking(Booking booking) {
    bookingValidator.isInSlotRange(
        candidateService.getSlotById(booking.getCandidateSlot().getId()), booking);
    bookingValidator.isInSlotRange(
        interviewerService.getSlotById(booking.getInterviewerSlot().getId()), booking);
    bookingValidator.isOverLappingWithBookings(
        interviewerService.getSlotById(booking.getInterviewerSlot().getId()).getBooking().stream()
            .filter(b -> !Objects.equals(
                b.getId(), booking.getId())).collect(
                Collectors.toSet()), booking);
    bookingValidator.isOverLappingWithBookings(
        candidateService.getSlotById(booking.getCandidateSlot().getId()).getBooking().stream()
            .filter(b -> !Objects.equals(b.getId(), booking.getId())).collect(
                Collectors.toSet()), booking);
    return repository.save(booking);
  }

  public List<Booking> findByInterviewerIdAndWeekNum(Long interviewerId, int weekNum) {
    return repository.findByInterviewerIdAndWeekNum(interviewerId, weekNum);
  }

}
