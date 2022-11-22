package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.models.Candidate;
import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import com.intellias.intellistart.interviewplanning.repositories.CandidateRepository;
import com.intellias.intellistart.interviewplanning.repositories.CandidateSlotRepository;
import com.intellias.intellistart.interviewplanning.util.exceptions.CandidateNotFoundException;
import com.intellias.intellistart.interviewplanning.util.exceptions.CandidateSlotNotFoundException;
import com.intellias.intellistart.interviewplanning.util.exceptions.UserNotFoundException;
import com.intellias.intellistart.interviewplanning.util.validation.CandidateValidator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Candidate entity service.
 */
@Service
@Transactional
public class CandidateService {

  private final CandidateRepository repository;
  private final CandidateSlotRepository slotRepository;

  private final WeekService weekService;

  @Autowired
  private ModelMapper mapper;

  /**
   * CandidateService constructor.
   */
  @Autowired
  public CandidateService(
      CandidateRepository repository,
      CandidateSlotRepository slotRepository,
      WeekService weekService) {
    this.repository = repository;
    this.slotRepository = slotRepository;
    this.weekService = weekService;
  }

  /**
   * Method for getting slot by id.
   *
   * @param id slot id
   * @return slot entity
   */
  public CandidateSlot getSlotById(Long id) {
    return slotRepository.findById(id).orElseThrow(CandidateSlotNotFoundException::new);
  }

  public List<CandidateSlot> getAllSlots() {
    return slotRepository.findAll();
  }

  public void deleteSlot(Long id) {
    getSlotById(id).getCandidate().getCandidateSlot().remove(getSlotById(id));
    slotRepository.deleteById(id);
  }

  public void deleteSlotsById(List<Long> ids) {
    slotRepository.deleteAllById(ids);
  }

  /**
   * Register slot and checks its overlapping with other slots and bookings.
   */
  public CandidateSlot registerSlot(CandidateSlot slot) {
    if (slot.getCandidate() == null) {
      throw new CandidateNotFoundException();
    }
    CandidateValidator.validateCandidateSlotForBoundaries(slot);
    CandidateValidator.validateCandidateSlotForOverlapping(
        getCandidateById(slot.getCandidate().getId()).getCandidateSlot().stream()
            .filter(s -> !Objects.equals(s.getId(), slot.getId())).collect(Collectors.toSet()),
        slot);
    return slotRepository.save(slot);
  }

  /**
   * Method for getting candidate by id.
   *
   * @param id slot id
   * @return find result - slot
   */
  public Candidate getCandidateById(Long id) {
    return repository.findById(id).orElseThrow(CandidateSlotNotFoundException::new);
  }

  public Candidate getCandidateByUserId(Long userId) {
    return repository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
  }

  public List<Candidate> getAllCandidates() {
    return repository.findAll();
  }

  public void deleteCandidate(Long id) {
    repository.deleteById(id);
  }

  public void deleteCandidatesById(List<Long> ids) {
    repository.deleteAllById(ids);
  }

  public Candidate registerCandidate(Candidate candidate) {
    return repository.save(candidate);
  }

  public List<Candidate> registerCandidates(List<Candidate> candidates) {
    return repository.saveAll(candidates);
  }

  /**
   * Gets all candidate slots that are arranged on a particular week.
   */
  public List<CandidateSlot> getCandidateSlotsForWeek(int weekNum) {
    return slotRepository.findAll().stream()
        .filter(slot -> weekService.getWeekNumFrom(slot.getDateFrom().toLocalDate()) == weekNum
            || weekService.getWeekNumFrom(slot.getDateTo().toLocalDate()) == weekNum)
        .collect(Collectors.toList());
  }

  /**
   * Gets all candidate slots that are arranged on a particular week and day.
   */
  public List<CandidateSlot> getCandidateSlotsForWeekAndDayOfWeek(int weekNum, int dayOfWeek) {
    return slotRepository.findAll().stream()
        .filter(slot -> getWeekNumOfCandidateSlot(slot) == weekNum)
        .filter(slot -> getDayOfWeekOfCandidateSlot(slot) == dayOfWeek)
        .collect(Collectors.toList());
  }

  /**
   * Gets week number of candidate slot.
   */
  public int getWeekNumOfCandidateSlot(CandidateSlot candidateSlot) {
    return weekService.getWeekNumFrom(candidateSlot.getDateFrom().toLocalDate());
  }

  /**
   * Gets day of week number of candidate slot.
   */
  public int getDayOfWeekOfCandidateSlot(CandidateSlot candidateSlot) {
    return weekService.getDayOfWeekFrom(candidateSlot.getDateFrom().toLocalDate());
  }
}
