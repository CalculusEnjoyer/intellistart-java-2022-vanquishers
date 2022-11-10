package com.intellias.intellistart.interviewplanning.util.validation;

import com.intellias.intellistart.interviewplanning.controllers.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.util.exceptions.ForbiddenException;
import com.intellias.intellistart.interviewplanning.util.exceptions.InterviewApplicationException;
import com.intellias.intellistart.interviewplanning.util.exceptions.InvalidSlotBoundariesException;
import java.time.LocalTime;
import java.util.Objects;
import org.springframework.http.HttpStatus;

/**
 * Validator for interviewers and interviewer-related data.
 */
public class InterviewerValidator {
  /**
   * Private constructor to hide the ability of instantiation of a utility class.
   */
  private InterviewerValidator() {}

  /**
   * Method that validates the maximum amount of bookings.
   * It should be a positive integer.
   *
   * @param maxBookings maximum amount of bookings.
   * @throws InterviewApplicationException if maxBookings is not valid
   */
  public static void validateBookingLimit(Integer maxBookings) {
    if (maxBookings == null || maxBookings < 1) {
      throw new InterviewApplicationException(
          "bad_request", HttpStatus.BAD_REQUEST, "maxBookings should be a positive int");
    }
  }

  /**
   * Validates that the given slotWeekNum is equal to nextWeekNum.
   *
   * @param slotWeekNum week number of a slot
   * @param nextWeekNum next week number
   * @throws InterviewApplicationException slotWeekNum and nextWeekNum doesn't match
   */
  public static void validateSlotWeekNum(int slotWeekNum, int nextWeekNum) {
    if (slotWeekNum != nextWeekNum) {
      throw new InterviewApplicationException(
          "bad_request", HttpStatus.BAD_REQUEST,
          "Invalid weekNum: slot can be created only for next week: " + nextWeekNum);
    }
  }

  /**
   * Validates the all data in the slotDto dto.
   * Throws exceptions if data in the slotDto is invalid, otherwise does nothing.
   *
   * @param slotDto slotDto to validate
   * @throws InvalidSlotBoundariesException
   *          if the time boundaries of the provided slotDto are invalid
   */
  public static void validateSlotDto(InterviewerSlotDto slotDto) {
    validateDuration(slotDto.getTimeFrom(), slotDto.getTimeTo());
  }

  private static void validateDuration(LocalTime from, LocalTime to) {
    if (!UtilValidator.isValidTimeBoundaries(from, to)) {
      throw new InvalidSlotBoundariesException("Invalid slot time boundaries");
    }
  }

  /**
   * Validates that interviewer with provided {@code interviewerId}
   * can access the provided {@code slot}.
   *
   * @param interviewerId id of the interviewer who tries to access the slot
   * @param slot interviewer slot to which the interviewer tries to acquire the access
   * @throws ForbiddenException if interviewer with provided {@code interviewerId}
   *        has no access to the provided slot
   */
  public static void validateHasAccessToSlot(Long interviewerId, InterviewerSlot slot) {
    Long interviewerIdFromSlot = slot.getInterviewer().getId();
    if (!Objects.equals(interviewerId, interviewerIdFromSlot)) {
      throw new ForbiddenException("Interviewer with id " + interviewerId
          + " has no access to slot with id " + slot.getId());
    }
  }
}
