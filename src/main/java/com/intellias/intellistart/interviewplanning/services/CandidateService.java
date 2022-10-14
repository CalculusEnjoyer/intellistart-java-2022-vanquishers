package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import com.intellias.intellistart.interviewplanning.repositories.CandidateSlotRepository;
import java.util.List;
import java.util.Optional;
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
  public CandidateService(CandidateSlotRepository slotRepository) {
    this.slotRepository = slotRepository;
  }

  public List<CandidateSlot> findAll() {
    return slotRepository.findAll();
  }

  public void deleteSlot(Long id) {
    slotRepository.deleteById(id);
  }

  public void deleteAll() {
    slotRepository.deleteAll();
  }

  public CandidateSlot registerSlot(CandidateSlot slot) {
    return slotRepository.save(slot);
  }

  public List<CandidateSlot> registerAll(List<CandidateSlot> slots) {
    return slotRepository.saveAll(slots);
  }

  public Optional<CandidateSlot> findById(Long id) {
    return slotRepository.findById(id);
  }
}
