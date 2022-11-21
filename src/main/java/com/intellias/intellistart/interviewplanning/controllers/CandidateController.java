package com.intellias.intellistart.interviewplanning.controllers;

import com.intellias.intellistart.interviewplanning.controllers.dto.CandidateSlotDto;
import com.intellias.intellistart.interviewplanning.models.Candidate;
import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import com.intellias.intellistart.interviewplanning.models.security.FacebookUserDetails;
import com.intellias.intellistart.interviewplanning.services.CandidateService;
import com.intellias.intellistart.interviewplanning.util.exceptions.CandidateSlotNotFoundException;
import com.intellias.intellistart.interviewplanning.util.exceptions.InvalidSlotBoundariesException;
import com.intellias.intellistart.interviewplanning.util.exceptions.SlotAccessException;
import com.intellias.intellistart.interviewplanning.util.models.CandidateSlotForm;
import com.intellias.intellistart.interviewplanning.util.validation.CandidateSlotValidator;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("all")
/**
 * Candidate controller.
 */
@RestController
@RequestMapping(CandidateController.MAPPING)
public class CandidateController {

  public static final String MAPPING = "/candidates";

  public final CandidateService candidateService;
  public final ModelMapper mapper;

  /**
   * Autowired constructor for candidate controller.
   *
   * @param candidateService candidate service with needed methods
   * @param userService
   * @param mapper           for mapping DTOs to entities and vice versa
   */
  @Autowired
  public CandidateController(
      CandidateService candidateService,
      ModelMapper mapper) {

    this.candidateService = candidateService;
    this.mapper = mapper;
  }

  /**
   * Method for adding time slot(s) for candidate.
   *
   * @return response status
   */
  @PostMapping("/current/slots")
  public ResponseEntity<Object> addSlot(
      Authentication authentication,
      @RequestBody CandidateSlotDto candidateSlotDto) {

    if (!CandidateSlotValidator.isValidCandidateSlot(candidateSlotDto)) {
      throw new InvalidSlotBoundariesException();
    }

    FacebookUserDetails details = (FacebookUserDetails) authentication.getPrincipal();
    Candidate candidate = candidateService.getCandidateByUserId(details.getUser().getId());

    CandidateSlot slot = mapper.map(candidateSlotDto, CandidateSlot.class);
    slot.setCandidate(candidate);
    candidateService.registerSlot(slot);

    CandidateSlotDto dto = mapper.map(slot, CandidateSlotDto.class);
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  /**
   * Method for updating time slot for candidate.
   *
   * @return response status
   */
  @PostMapping("/current/slots/{slotId}")
  public ResponseEntity<CandidateSlotDto> updateSlot(
      Authentication authentication,
      @RequestBody CandidateSlotDto candidateSlotDto,
      @PathVariable Long slotId) {

    if (!CandidateSlotValidator.isValidCandidateSlot(candidateSlotDto)) {
      throw new InvalidSlotBoundariesException();
    }

    FacebookUserDetails details = (FacebookUserDetails) authentication.getPrincipal();
    Candidate candidate = candidateService.getCandidateByUserId(details.getUser().getId());

    CandidateSlot slot = candidateService.getSlotById(slotId);
    if (slot.getCandidate().getUser().getId() != candidate.getUser().getId()) {
      throw new SlotAccessException();
    }

    slot.setDateFrom(candidateSlotDto.getDateFrom());
    slot.setDateTo(candidateSlotDto.getDateTo());
    candidateService.registerSlot(slot);

    CandidateSlotDto dto = mapper.map(slot, CandidateSlotDto.class);
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  /**
   * Method for getting all time slots by candidate.
   *
   * @return list of candidate time slots
   */
  @GetMapping("/current/slots")
  public List<CandidateSlotForm> getAllSlots(Authentication authentication) {

    FacebookUserDetails details = (FacebookUserDetails) authentication.getPrincipal();

    return candidateService.getAllSlots().stream()
        .filter(e -> e.getCandidate().getUser().getId() == details.getUser().getId())
        .map(e -> new CandidateSlotForm(e))
        .collect(Collectors.toList());
  }

}
