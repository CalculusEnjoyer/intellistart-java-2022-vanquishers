package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.dto.TimeSlotDto;
import com.intellias.intellistart.interviewplanning.util.TimeSlotForm;
import java.time.DayOfWeek;
import java.time.LocalTime;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * Coordinator entity service.
 */
@Service
@Transactional
public class CoordinatorService {

  public TimeSlotDto createSlot(int weekNum, DayOfWeek dayOfWeek, LocalTime from, LocalTime to) {
    return new TimeSlotDto(weekNum, dayOfWeek, from, to);
  }

  public TimeSlotDto createSlot(TimeSlotForm.TimeSlotFormBuilder timeSlotFormBuilder) {
    return TimeSlotDto.of(timeSlotFormBuilder.build());
  }

}
