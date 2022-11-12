package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.models.Interviewer;
import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.repositories.InterviewerRepository;
import com.intellias.intellistart.interviewplanning.repositories.InterviewerSlotRepository;
import com.intellias.intellistart.interviewplanning.util.exceptions.InterviewerNotFoundException;
import com.intellias.intellistart.interviewplanning.util.exceptions.InterviewerSlotNotFoundException;
import com.intellias.intellistart.interviewplanning.util.exceptions.UserNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Interviewer entity service.
 */
@Service
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

  public Interviewer registerInterviewer(Interviewer interviewer) {
    return interviewerRepository.save(interviewer);
  }

  public Interviewer getInterviewerById(Long id) {
    return interviewerRepository.findById(id)
        .orElseThrow(InterviewerNotFoundException::new);
  }

  /**
   * Method for getting slot by id.
   *
   * @param id slot id
   * @return deleted slot
   */
  public InterviewerSlot getSlotById(Long id) {
    return slotRepository.findById(id).orElseThrow(InterviewerSlotNotFoundException::new);
  }

  public List<InterviewerSlot> getAllSlots() {
    return slotRepository.findAll();
  }

  public void deleteInterviewerById(Long id) {
    interviewerRepository.deleteById(id);
  }

  public void deleteSlotById(Long id) {
    slotRepository.deleteById(id);
  }

  public void deleteSlotsById(List<Long> ids) {
    slotRepository.deleteAllById(ids);
  }

  public Interviewer getInterviewerByUserId(Long userId) {
    return interviewerRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
  }

  public InterviewerSlot registerSlot(InterviewerSlot slot) {
    return slotRepository.save(slot);
  }

  public List<InterviewerSlot> registerSlots(List<InterviewerSlot> slots) {
    return slotRepository.saveAll(slots);
  }

  public List<InterviewerSlot> getSlotsForIdAndWeek(Long interviewerId, int weekNum) {
    return slotRepository.findByInterviewerIdAndWeekNum(interviewerId, weekNum);
  }

  public List<InterviewerSlot> getSlotsForWeek(int weekNum) {
    return slotRepository.findByWeekNum(weekNum);
  }

  public List<InterviewerSlot> getSlotsForWeekAndDayOfWeek(int weekNum, int dayOfWeek) {
    return slotRepository.findByWeekNumAndDayOfWeek(weekNum, dayOfWeek);
  }
}
