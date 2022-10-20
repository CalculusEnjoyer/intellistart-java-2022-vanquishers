package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.repositories.InterviewerSlotRepository;
import java.util.List;
import java.util.Optional;
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
  public InterviewerService(InterviewerSlotRepository slotRepository) {
    this.slotRepository = slotRepository;
  }

  public Optional<InterviewerSlot> findById(Long id) {
    return slotRepository.findById(id);
  }

  public List<InterviewerSlot> findAll() {
    return slotRepository.findAll();
  }

  public void delete(Long id) {
    slotRepository.deleteById(id);
  }

  public void deleteAll() {
    slotRepository.deleteAll();
  }

  public InterviewerSlot register(InterviewerSlot slot) {
    return slotRepository.save(slot);
  }

  public List<InterviewerSlot> registerAll(List<InterviewerSlot> slots) {
    return slotRepository.saveAll(slots);
  }

}
