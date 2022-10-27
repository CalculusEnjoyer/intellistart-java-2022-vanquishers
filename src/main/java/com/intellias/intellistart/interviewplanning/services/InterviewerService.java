package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.models.Interviewer;
import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.repositories.InterviewerRepository;
import com.intellias.intellistart.interviewplanning.repositories.InterviewerSlotRepository;
import com.intellias.intellistart.interviewplanning.util.exceptions.InterviewerSlotNotFoundException;
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

  private final InterviewerRepository interviewerRepository;

  @Autowired
  public InterviewerService(InterviewerSlotRepository slotRepository,
      InterviewerRepository interviewerRepository) {
    this.slotRepository = slotRepository;
    this.interviewerRepository = interviewerRepository;
  }

  public List<Interviewer> getAllInterviewers() {
    return interviewerRepository.findAll();
  }

  public InterviewerSlot getSlotById(Long id) {
    Optional<InterviewerSlot> resultSlot = slotRepository.findById(id);
    if (resultSlot.isPresent()) {
      return resultSlot.get();
    } else {
      throw new InterviewerSlotNotFoundException();
    }
  }

  public List<InterviewerSlot> getAllSlots() {
    return slotRepository.findAll();
  }

  public void deleteSlotById(Long id) {
    slotRepository.deleteById(id);
  }

  public void deleteSlotsById(List<Long> ids) {
    slotRepository.deleteAllById(ids);
  }

  public InterviewerSlot registerSlot(InterviewerSlot slot) {
    return slotRepository.save(slot);
  }

  public List<InterviewerSlot> registerSlots(List<InterviewerSlot> slots) {
    return slotRepository.saveAll(slots);
  }

}
