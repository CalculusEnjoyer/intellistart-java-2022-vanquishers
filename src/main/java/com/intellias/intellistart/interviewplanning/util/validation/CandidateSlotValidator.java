package com.intellias.intellistart.interviewplanning.util.validation;

import com.intellias.intellistart.interviewplanning.controllers.dto.CandidateSlotDto;
import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import com.intellias.intellistart.interviewplanning.util.exceptions.InvalidSlotBoundariesException;
import com.intellias.intellistart.interviewplanning.util.exceptions.OverlappingSlotException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Validator for CandidateSlot DTOs.
 */
public class CandidateSlotValidator {

  /**
   * Private constructor to hide the ability of instantiation of a utility class.
   */
  private CandidateSlotValidator() {
  }

  /**
   * Method for checking DTO validity.
   *
   * @param dto DTO to be checked via next conditions: 1) future time (tomorrow or later) 2)
   *            duration >= 1.5 hours 3) start hour is 8-22 (exclusive 22) 4) end hour is 8-22
   *            (inclusive) 5) minute, passed by user can only be :00 or :30
   * @return true if this DTO valid for adjustment
   */
  public static boolean isValidCandidateSlot(CandidateSlotDto dto) {
    LocalDateTime fromTime = dto.getDateFrom();
    LocalDateTime toTime = dto.getDateTo();
    return dto.getDateFrom().toLocalDate().isAfter(LocalDate.now())
        && UtilValidator.isValidTimeBoundaries(fromTime.toLocalTime(), toTime.toLocalTime());
  }

  /**
   * Method for getting valid DTO.
   *
   * @param dto DTO that needs to be adjusted and checked
   * @return adjusted DTO
   */
  public static CandidateSlotDto validDtoOrError(CandidateSlotDto dto) {
    if (!isValidCandidateSlot(dto)) {
      throw new InvalidSlotBoundariesException();
    }
    return dto;
  }

  /**
   * Checks if slot do not overlap with already existing Candidates's slots.
   */
  public static void validateCandidateSlotForOverlapping(Set<CandidateSlot> candidateSlots,
      CandidateSlotDto candidateSlotDto) {
    for (CandidateSlot slot : candidateSlots) {
      if (isTimeInSlotBoundaries(candidateSlotDto.getDateFrom(), slot)
          || isTimeInSlotBoundaries(candidateSlotDto.getDateTo(), slot)
          || isTimeInSlotBoundaries(slot.getDateFrom(), candidateSlotDto)
          || (slot.getDateFrom().equals(candidateSlotDto.getDateFrom())
          && slot.getDateTo().equals(candidateSlotDto.getDateTo()))) {
        throw new OverlappingSlotException();
      }
    }
  }

  private static boolean isTimeInSlotBoundaries(LocalDateTime time,
      CandidateSlot candidateSlot) {
    return time.compareTo(candidateSlot.getDateFrom()) > 0
        && time.compareTo(candidateSlot.getDateTo()) < 0;
  }

  private static boolean isTimeInSlotBoundaries(LocalDateTime time,
      CandidateSlotDto candidateSlotDto) {
    return time.compareTo(candidateSlotDto.getDateFrom()) > 0
        && time.compareTo(candidateSlotDto.getDateTo()) < 0;
  }
}
