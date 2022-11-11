package com.intellias.intellistart.interviewplanning.repositories;

import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interviewer slot repository.
 */
@Repository
public interface InterviewerSlotRepository extends JpaRepository<InterviewerSlot, Long> {

  List<InterviewerSlot> findByInterviewerIdAndWeekNum(Long interviewerId, int weekNum);

  List<InterviewerSlot> findByWeekNumAndDayOfWeek(int weekNum, int dayOfWeek);

  List<InterviewerSlot> findByWeekNum(int weekNum);
}
