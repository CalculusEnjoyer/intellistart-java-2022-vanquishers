package com.intellias.intellistart.interviewplanning.repositories;

import com.intellias.intellistart.interviewplanning.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Booking repository.
 */

public interface BookingRepository extends JpaRepository<Booking, Long> {

}