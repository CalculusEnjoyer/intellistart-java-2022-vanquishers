package com.intellias.intellistart.interviewplanning.repositories;

import com.intellias.intellistart.interviewplanning.models.Booking;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Booking repository.
 */

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

  @Query(value = "select *\n"
      + "from bookings b\n"
      + "         join interviewer_slots i on i.id = b.interviewer_slots_id\n"
      + "         join interviewers i2 on i2.id = i.interviewer_id\n"
      + "where week_num = ?2 and i2.id = ?1",
      nativeQuery = true)
  List<Booking> findByInterviewerIdAndWeekNum(Long interviewerId, int weekNum);
}