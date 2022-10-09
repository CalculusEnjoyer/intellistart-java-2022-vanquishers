package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.repositories.InterviewerSlotRepository;
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

  public void deleteSlot(Long id) {
    slotRepository.deleteById(id);
  }

  public void deleteAll() {
    slotRepository.deleteAll();
  }

  public void register(InterviewerSlot slot) {
    slotRepository.save(slot);
  }

  public void registerAll(List<InterviewerSlot> slots) {
    slotRepository.saveAll(slots);
  }

}
