package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.dto.TimeSlotDto;
import com.intellias.intellistart.interviewplanning.models.TimeSlot;
import com.intellias.intellistart.interviewplanning.repositories.TimeSlotRepository;
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

  private final TimeSlotRepository timeSlotRepository;

  @Autowired
  public InterviewerService(TimeSlotRepository timeSlotRepository)  {
    this.timeSlotRepository = timeSlotRepository;
  }

  public List<TimeSlot> findAll() {
    return timeSlotRepository.findAll();
  }

  public void deleteTimeSlot(Long id) {
    timeSlotRepository.deleteById(id);
  }

  public void registerTimeSlot(TimeSlot timeSlot) {
    timeSlotRepository.save(timeSlot);
  }

  public TimeSlotDto createSlot(int weekNum, DayOfWeek dayOfWeek, LocalTime from, LocalTime to) {
    return new TimeSlotDto(weekNum, dayOfWeek, from, to);
  }

}
