package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.repositories.InterviewerSlotRepository;
import java.util.List;
import java.util.stream.Collectors;
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

  public List<InterviewerSlotDto> findAll() {
    return slotRepository.findAll().stream()
        .map(InterviewerSlotDto::of).collect(Collectors.toList());
  }

  public void deleteSlot(Long id) {
    slotRepository.deleteById(id);
  }

  public void deleteAll() {
    slotRepository.deleteAll();
  }

  public void register(InterviewerSlotDto slot) {
    slotRepository.save(InterviewerSlot.of(slot));
  }

  /**
   * Registering of many slots.

   * @param slots time slots list to register
   */
  public void registerAll(List<InterviewerSlotDto> slots) {
    slotRepository.saveAll(
        slots.stream()
            .map(InterviewerSlot::of).collect(Collectors.toList())
    );
  }

}
