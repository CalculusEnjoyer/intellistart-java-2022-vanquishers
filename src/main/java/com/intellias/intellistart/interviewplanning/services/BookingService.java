package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.models.Booking;
import com.intellias.intellistart.interviewplanning.repositories.BookingRepository;
import com.intellias.intellistart.interviewplanning.util.exceptions.BookingNotFoundException;
import java.util.List;
import java.util.Optional;
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

  @Autowired
  public BookingService(BookingRepository repository) {
    this.repository = repository;
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
    if (repository.existsById(id)) {
      repository.deleteById(id);
    } else {
      throw new BookingNotFoundException();
    }
  }

  public void deleteBookingsById(List<Long> ids) {
    repository.deleteAllById(ids);
  }

  public Booking registerBooking(Booking booking) {
    return repository.save(booking);
  }

  public List<Booking> registerBookings(List<Booking> bookings) {
    return repository.saveAll(bookings);
  }

}
