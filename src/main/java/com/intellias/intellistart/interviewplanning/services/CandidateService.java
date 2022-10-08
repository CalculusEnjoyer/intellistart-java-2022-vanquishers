package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.dto.CandidateSlotDto;
import com.intellias.intellistart.interviewplanning.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.models.Candidate;
import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.repositories.CandidateSlotRepository;
import java.util.List;
import java.util.stream.Collectors;
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

  public List<CandidateSlotDto> findAll() {
    return slotRepository.findAll().stream()
        .map(CandidateSlotDto::of).collect(Collectors.toList());
  }

  public void deleteSlot(Long id) {
    slotRepository.deleteById(id);
  }

  public void deleteAll() {
    slotRepository.deleteAll();
  }

  public void registerSlot(CandidateSlotDto slot) {
    slotRepository.save(CandidateSlot.of(slot));
  }


  /**
   * Registering of many slots.

   * @param slots time slots list to register
   */
  public void registerAll(List<CandidateSlotDto> slots) {
    slotRepository.saveAll(
        slots.stream()
            .map(CandidateSlot::of).collect(Collectors.toList())
    );
  }

}
