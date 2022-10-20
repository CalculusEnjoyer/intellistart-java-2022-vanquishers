package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.models.Booking;
import com.intellias.intellistart.interviewplanning.repositories.BookingRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Candidate entity service.
 */
@Service
@Transactional
public class BookingService {

  private final BookingRepository repository;

  @Autowired
  public BookingService(BookingRepository repository) {
    this.repository = repository;
  }

  public List<Booking> findAll() {
    return repository.findAll();
  }

  public void delete(Long id) {
    repository.deleteById(id);
  }

  public void deleteAll() {
    repository.deleteAll();
  }

  public Booking register(Booking booking) {
    return repository.save(booking);
  }

  public List<Booking> registerAll(List<Booking> bookings) {
    return repository.saveAll(bookings);
  }

  public Optional<Booking> findById(Long id) {
    return repository.findById(id);
  }

}
