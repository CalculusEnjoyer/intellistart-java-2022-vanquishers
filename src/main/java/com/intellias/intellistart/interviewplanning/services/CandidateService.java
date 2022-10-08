package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import com.intellias.intellistart.interviewplanning.repositories.CandidateSlotRepository;
import com.intellias.intellistart.interviewplanning.util.TimeSlotForm;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Candidate entity service.
 */
@Service
@Transactional
public class CandidateService {

  private final CandidateSlotRepository slotRepository;

  @Autowired
  public CandidateService(CandidateSlotRepository slotRepository)  {
    this.slotRepository = slotRepository;
  }

  public List<CandidateSlot> findAll() {
    return slotRepository.findAll();
  }

  public void deleteTimeSlot(Long id) {
    slotRepository.deleteById(id);
  }

  public void registerTimeSlot(CandidateSlot slot) {
    slotRepository.save(slot);
  }

  /*
  public TimeSlotDto createSlot(int weekNum, DayOfWeek dayOfWeek, LocalTime from, LocalTime to) {
    return new TimeSlotDto(weekNum, dayOfWeek, from, to);
  }

  public TimeSlotDto createSlot(TimeSlotForm.TimeSlotFormBuilder timeSlotFormBuilder) {
    return TimeSlotDto.of(timeSlotFormBuilder.build());
  }

   */

}
