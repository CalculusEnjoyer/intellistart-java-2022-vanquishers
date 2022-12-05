package com.intellias.intellistart.interviewplanning.util.validation;

import com.intellias.intellistart.interviewplanning.controllers.dto.CandidateSlotDto;
import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import com.intellias.intellistart.interviewplanning.services.WeekService;
import com.intellias.intellistart.interviewplanning.util.exceptions.InvalidSlotBoundariesException;
import com.intellias.intellistart.interviewplanning.util.exceptions.OverlappingSlotException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Validator for CandidateSlot DTOs.
 */
@Component
public class CandidateValidator {

  private final WeekService weekService;

  /**
   * Private constructor to hide the ability of instantiation of a utility class.
   */
  @Autowired
  private CandidateValidator(WeekService weekService) {
    this.weekService = weekService;
  }

  /**
   * DTO validation method.
   *
   * @param dto slot to be checked via next conditions: 1) future time (tomorrow or later)
   *             2) duration >= 1.5 hours 3) start hour is 8-22 (exclusive 22)
   *             4) end hour is 8-22 (inclusive) 5) minute, passed by user
   *             can only be :00 or :30
   */
  public void validateCandidateSlotForBoundaries(CandidateSlotDto dto) {
    LocalDateTime fromTime = dto.getDateFrom();
    LocalDateTime toTime = dto.getDateTo();

    boolean isFuture = fromTime.toLocalDate()
        .isAfter(LocalDate.now(weekService.getZoneId()))
        && toTime.isAfter(fromTime);
    boolean isValidBounds = UtilValidator.isValidTimeBoundaries(
        fromTime.toLocalTime(), toTime.toLocalTime());
    boolean isValid = isFuture && isValidBounds;

    if (!isValid) {
      throw new InvalidSlotBoundariesException();
    }
  }

  /**
   * Slot validation method.
   *
   * @param slot slot to be checked via next conditions: 1) future time (tomorrow or later)
   *             2) duration >= 1.5 hours 3) start hour is 8-22 (exclusive 22)
   *             4) end hour is 8-22 (inclusive) 5) minute, passed by user
   *             can only be :00 or :30
   */
  public void validateCandidateSlotForBoundaries(CandidateSlot slot) {
    CandidateSlotDto dto = new CandidateSlotDto();
    dto.setDateFrom(slot.getDateFrom());
    dto.setDateTo(slot.getDateTo());
    validateCandidateSlotForBoundaries(dto);
  }

  /**
   * Checks if slot do not overlap with already existing Candidates's slots.
   */
  public void validateCandidateSlotForOverlapping(Set<CandidateSlot> candidateSlots,
      CandidateSlotDto candidateSlotDto) {
    for (CandidateSlot slot : candidateSlots) {
      if (UtilValidator.areIntervalsOverLapping(slot.getDateFrom(), slot.getDateTo(),
          candidateSlotDto.getDateFrom(), candidateSlotDto.getDateTo())) {
        throw new OverlappingSlotException();
      }
    }
  }

  /**
   * Checks if slot do not overlap with already existing Candidates's slots.
   */
  public void validateCandidateSlotForOverlapping(Set<CandidateSlot> candidateSlots,
      CandidateSlot candidateSlot) {
    for (CandidateSlot slot : candidateSlots) {
      if (UtilValidator.areIntervalsOverLapping(slot.getDateFrom(), slot.getDateTo(),
          candidateSlot.getDateFrom(), candidateSlot.getDateTo())) {
        throw new OverlappingSlotException();
      }
    }
  }

}
