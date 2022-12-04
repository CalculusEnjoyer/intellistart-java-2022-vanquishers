package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.models.Interviewer;
import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.repositories.InterviewerRepository;
import com.intellias.intellistart.interviewplanning.repositories.InterviewerSlotRepository;
import com.intellias.intellistart.interviewplanning.util.exceptions.InterviewerNotFoundException;
import com.intellias.intellistart.interviewplanning.util.exceptions.InterviewerSlotNotFoundException;
import com.intellias.intellistart.interviewplanning.util.exceptions.UserNotFoundException;
import com.intellias.intellistart.interviewplanning.util.validation.InterviewerValidator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interviewer entity service.
 */
@Service
public class InterviewerService {

  private final InterviewerSlotRepository slotRepository;
  private final InterviewerRepository interviewerRepository;
  private final InterviewerValidator interviewerValidator;

  /**
   * Interviewer service constructor.
   */
  @Autowired
  public InterviewerService(InterviewerSlotRepository slotRepository,
      InterviewerRepository interviewerRepository, InterviewerValidator interviewerValidator) {
    this.slotRepository = slotRepository;
    this.interviewerRepository = interviewerRepository;
    this.interviewerValidator = interviewerValidator;
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

  /**
   * Remove slot from database.

   * @param id slot id
   */
  @Transactional
  public void deleteSlotById(Long id) {
    InterviewerSlot slot = getSlotById(id);
    slot.getInterviewer().getInterviewerSlot().remove(slot);
    slotRepository.deleteById(id);
  }

  public Interviewer getInterviewerByUserId(Long userId) {
    return interviewerRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
  }

  /**
   * Register slot and checks if it overlaps with other slots.
   */
  public InterviewerSlot registerSlot(InterviewerSlot slot) {
    if (slot.getInterviewer() == null) {
      throw new InterviewerNotFoundException();
    }
    interviewerValidator.validateOverLappingOfSlots(
        getInterviewerById(slot.getInterviewer().getId()).getInterviewerSlot().stream()
            .filter(s -> !Objects.equals(s.getId(), slot.getId())).collect(Collectors.toSet()),
        slot);
    return slotRepository.save(slot);
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
