package com.intellias.intellistart.interviewplanning.repositories;

import com.intellias.intellistart.interviewplanning.models.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TimeSlot entity repository.
 */
@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

}
