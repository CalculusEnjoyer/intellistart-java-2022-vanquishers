package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.models.Booking;
import com.intellias.intellistart.interviewplanning.repositories.BookingRepository;
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

  public Optional<Booking> getBookingById(Long id) {
    return repository.findById(id);
  }

  public List<Booking> getAllBookings() {
    return repository.findAll();
  }

  public void deleteBookingById(Long id) {
    repository.deleteById(id);
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
