package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.repositories.InterviewerSlotRepository;
import com.intellias.intellistart.interviewplanning.util.TimeSlotForm;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Interviewer entity service.
 */
@Service
@Transactional
public class InterviewerService {

  private final InterviewerSlotRepository slotRepository;

  @Autowired
  public InterviewerService(InterviewerSlotRepository slotRepository)  {
    this.slotRepository = slotRepository;
  }

  public List<InterviewerSlot> findAll() {
    return slotRepository.findAll();
  }

  public void deleteTimeSlot(Long id) {
    slotRepository.deleteById(id);
  }

  public void registerTimeSlot(InterviewerSlot timeSlot) {
    slotRepository.save(timeSlot);
  }

  /*
  public TimeSlotDto createSlot(int weekNum, DayOfWeek dayOfWeek, LocalTime from, LocalTime to) {
    return TimeSlotDto.of(weekNum, dayOfWeek, from, to);
  }

  public TimeSlotDto createSlot(TimeSlotForm.TimeSlotFormBuilder timeSlotFormBuilder) {
    return TimeSlotDto.of(timeSlotFormBuilder.build());
  }

   */

}
