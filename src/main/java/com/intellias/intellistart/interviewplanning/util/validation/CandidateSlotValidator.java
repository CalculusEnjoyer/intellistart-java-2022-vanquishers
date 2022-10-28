package com.intellias.intellistart.interviewplanning.util.validation;

import com.intellias.intellistart.interviewplanning.controllers.dto.CandidateSlotDto;
import com.intellias.intellistart.interviewplanning.util.exceptions.InvalidSlotBoundariesException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Validator for CandidateSlot DTOs.
 */
public class CandidateSlotValidator {

  /**
   * Method for checking DTO validity.

   * @param dto DTO to be checked via next conditions:
   *            1) future time (tomorrow or later)
   *            2) duration >= 1.5 hours
   *            3) start hour is 8-22 (exclusive 22)
   *            4) end hour is 8-22 (inclusive)
   *            5) minute, passed by user can only be :00 or :30
   *
   * @return true if this DTO valid for adjustment
   */
  public static boolean isValidCandidateSlot(CandidateSlotDto dto) {
    LocalDateTime fromTime = dto.getDateFrom();
    LocalDateTime toTime = dto.getDateTo();
    return dto.getDateFrom().toLocalDate().isAfter(LocalDate.now())
        && Duration.between(fromTime, toTime).toMinutes() >= 90
        && fromTime.getMinute() % 30 == 0 && toTime.getMinute() % 30 == 0
        && fromTime.getHour() >= 8 && fromTime.getHour() < 22
        && toTime.getHour() >= 8 && toTime.getHour() <= 22
        && toTime.getHour() > fromTime.getHour();
  }

  /**
   * Method for getting valid DTO.

   * @param dto DTO that needs to be adjusted and checked
   *
   * @return adjusted DTO
   */
  public static CandidateSlotDto validDtoOrError(CandidateSlotDto dto) {
    if (!isValidCandidateSlot(dto)) {
      throw new InvalidSlotBoundariesException();
    }
    return dto;
  }

}
