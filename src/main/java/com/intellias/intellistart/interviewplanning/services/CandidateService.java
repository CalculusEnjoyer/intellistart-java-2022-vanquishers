package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.models.Candidate;
import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import com.intellias.intellistart.interviewplanning.repositories.CandidateRepository;
import com.intellias.intellistart.interviewplanning.repositories.CandidateSlotRepository;
import com.intellias.intellistart.interviewplanning.util.exceptions.CandidateSlotNotFoundException;
import com.intellias.intellistart.interviewplanning.util.exceptions.UserNotFoundException;
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

  private final CandidateRepository repository;
  private final CandidateSlotRepository slotRepository;

  @Autowired
  public CandidateService(
      CandidateRepository repository,
      CandidateSlotRepository slotRepository) {
    this.repository = repository;
    this.slotRepository = slotRepository;
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
    slotRepository.deleteById(id);
  }

  public void deleteSlotsById(List<Long> ids) {
    slotRepository.deleteAllById(ids);
  }

  public CandidateSlot registerSlot(CandidateSlot slot) {
    return slotRepository.save(slot);
  }

  public List<CandidateSlot> registerSlots(List<CandidateSlot> slots) {
    return slotRepository.saveAll(slots);
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

  /**
   * Method for getting candidate by Facebook ID.
   *
   * @param facebookId facebook id
   * @return find result - candidate
   */
  public Candidate getCandidateByFacebookId(Long facebookId) {
    return repository.findByFacebookId(facebookId);
  }

  public Candidate getCandidateByUserId(Long userId){
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

  public List<Candidate> registerCandidates(List<Candidate> slots) {
    return repository.saveAll(slots);
  }

}
