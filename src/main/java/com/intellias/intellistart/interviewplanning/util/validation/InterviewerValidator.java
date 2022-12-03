package com.intellias.intellistart.interviewplanning.util.validation;

import com.intellias.intellistart.interviewplanning.controllers.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.models.enums.Role;
import com.intellias.intellistart.interviewplanning.services.WeekService;
import com.intellias.intellistart.interviewplanning.util.exceptions.ForbiddenException;
import com.intellias.intellistart.interviewplanning.util.exceptions.InterviewApplicationException;
import com.intellias.intellistart.interviewplanning.util.exceptions.InvalidSlotBoundariesException;
import com.intellias.intellistart.interviewplanning.util.exceptions.InvalidWeekNumException;
import com.intellias.intellistart.interviewplanning.util.exceptions.OverlappingSlotException;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Validator for interviewers and interviewer-related data.
 */
@Component
public class InterviewerValidator {

  private final WeekService weekService;

  @Autowired
  private InterviewerValidator(WeekService weekService) {
    this.weekService = weekService;
  }

  /**
   * Method that validates the maximum amount of bookings. It should be a positive integer.
   *
   * @param maxBookings maximum amount of bookings.
   * @throws InterviewApplicationException if maxBookings is not valid
   */
  public void validateBookingLimit(Integer maxBookings) {
    if (maxBookings == null || maxBookings < 1) {
      throw new InterviewApplicationException(
          "bad_request", HttpStatus.BAD_REQUEST, "maxBookings should be a positive int");
    }
  }

  /**
   * Validates all the data in the slotDto dto for slot creation by interviewer. Throws exceptions
   * if data in the slotDto is invalid, otherwise does nothing.
   *
   * @param slotDto slotDto to validate
   * @throws InvalidSlotBoundariesException if the time boundaries of the provided slotDto are
   *                                        invalid
   */
  public void validateSlotCreateForDto(InterviewerSlotDto slotDto) {
    validateSlotDuration(slotDto.getTimeFrom(), slotDto.getTimeTo());

    int slotWeekNum = slotDto.getWeekNum();
    validateIsNotPastWeek(slotWeekNum);
    validateIsNotCurrentWeek(slotWeekNum);
    validateIfNextWeekThenOnlyUntilFriday(slotWeekNum);
  }

  /**
   * Validates all the data in the slotDto dto for slot updating by given role. Throws exceptions if
   * data in the slotDto is invalid or given role is not allowed to update the slot, otherwise does
   * nothing.
   *
   * @param slotDto slotDto to validate
   * @param role    role that performs the slot update
   * @throws InvalidSlotBoundariesException if the time boundaries of the provided slotDto are
   *                                        invalid
   * @throws ForbiddenException             if not allowed role tried to update the slot
   */

  public void validateSlotUpdateForDtoAndRole(InterviewerSlotDto slotDto, Role role) {
    validateSlotDuration(slotDto.getTimeFrom(), slotDto.getTimeTo());
    validateSlotDtoWeekNumIsNotNull(slotDto.getWeekNum());
    validateSlotUpdateForWeekNumAndRole(slotDto.getWeekNum(), role);
  }

  private void validateSlotDtoWeekNumIsNotNull(Integer weekNum) {
    if (weekNum == null) {
      throw new InvalidWeekNumException("Specify the weekNum for slot update.");
    }
  }

  /**
   * Validates that slot with the provided {@code slotWeekNum} can be updated by user with provided
   * {@code role}. Throws exceptions if the validation fails.
   *
   * @param slotWeekNum slot weekNum to validate
   * @param role        role that performs the slot update
   * @throws ForbiddenException if not allowed role tried to update the slot
   */
  public void validateSlotUpdateForWeekNumAndRole(int slotWeekNum, Role role) {
    validateIsNotPastWeek(slotWeekNum);
    switch (role) {
      case COORDINATOR:
        break;
      case INTERVIEWER:
        validateIsNotCurrentWeek(slotWeekNum);
        validateIfNextWeekThenOnlyUntilFriday(slotWeekNum);
        break;
      default:
        throw new ForbiddenException("Unexpected role for interviewer slot updating: " + role);
    }
  }

  private void validateIsNotCurrentWeek(int slotWeekNum) {
    if (slotWeekNum == weekService.getCurrentWeekNum()) {
      throw new InvalidWeekNumException("Cannot create or update slot for current weekNum.");
    }
  }

  private void validateIsNotPastWeek(int slotWeekNum) {
    if (slotWeekNum < weekService.getCurrentWeekNum()) {
      throw new InvalidWeekNumException("Cannot create or update slot for past weekNum.");
    }
  }

  private void validateIfNextWeekThenOnlyUntilFriday(int slotWeekNum) {
    if (slotWeekNum == weekService.getNextWeekNum()
        && weekService.getCurrentDayOfWeek() > 4) {
      throw new InvalidWeekNumException(
          "Slot can be created or updated for next week only until Friday 00:00");
    }
  }


  private void validateSlotDuration(LocalTime from, LocalTime to) {
    if (!UtilValidator.isValidTimeBoundaries(from, to)) {
      throw new InvalidSlotBoundariesException("Invalid slot time boundaries");
    }
  }

  /**
   * Validates that interviewer with provided {@code interviewerId} can access the provided
   * {@code slot}.
   *
   * @param interviewerId id of the interviewer who tries to access the slot
   * @param slot          interviewer slot to which the interviewer tries to acquire the access
   * @throws ForbiddenException if interviewer with provided {@code interviewerId} has no access to
   *                            the provided slot
   */
  public void validateHasAccessToSlot(Long interviewerId, InterviewerSlot slot) {
    Long interviewerIdFromSlot = slot.getInterviewer().getId();
    if (!Objects.equals(interviewerId, interviewerIdFromSlot)) {
      throw new ForbiddenException("Interviewer with id " + interviewerId
          + " has no access to slot with id " + slot.getId());
    }
  }

  /**
   * Checks if slot do not overlap with already existing Interviewer's slots.
   */
  public void validateOverLappingOfSlots(Set<InterviewerSlot> interviewerSlots,
      InterviewerSlotDto interviewerSlotDto) {

    Set<InterviewerSlot> sameDayInterviewerSlots = interviewerSlots.stream().filter(
        slot -> slot.getDayOfWeek() == interviewerSlotDto.getDayOfWeek()
            && slot.getWeekNum() == interviewerSlotDto.getWeekNum()).collect(Collectors.toSet());

    for (InterviewerSlot slot : sameDayInterviewerSlots) {
      if (UtilValidator.areIntervalsOverLapping(slot.getFrom(), slot.getTo(),
          interviewerSlotDto.getTimeFrom(), interviewerSlotDto.getTimeTo())) {
        throw new OverlappingSlotException();
      }
    }
  }

  /**
   * Checks if slot do not overlap with already existing Interviewer's slots.
   */
  public void validateOverLappingOfSlots(Set<InterviewerSlot> interviewerSlots,
      InterviewerSlot interviewerSlot) {

    Set<InterviewerSlot> sameDayInterviewerSlots = interviewerSlots.stream().filter(
        slot -> slot.getDayOfWeek() == interviewerSlot.getDayOfWeek()
            && slot.getWeekNum() == interviewerSlot.getWeekNum()).collect(Collectors.toSet());

    for (InterviewerSlot slot : sameDayInterviewerSlots) {
      if (UtilValidator.areIntervalsOverLapping(slot.getFrom(), slot.getTo(),
          interviewerSlot.getFrom(), interviewerSlot.getTo())) {
        throw new OverlappingSlotException();
      }
    }
  }

  /**
   * Validates that user id of the interviewer authenticated in, matches the user id of the
   * interviewer in the request path. Throws an exception if it's not, otherwise does nothing.
   *
   * @param authUserId id of the interviewer authenticated in
   * @param pathUserId id of the interviewer in the request path
   * @throws ForbiddenException if the IDs are not matching (so Interviewer tries to access other
   *                            Interviewer's data
   */
  public void validateUserIdMatch(Long authUserId, Long pathUserId) {
    if (!pathUserId.equals(authUserId)) {
      throw new ForbiddenException("Interviewer with user id: " + authUserId
          + " tried to access the other user (" + pathUserId + ") data in request");
    }
  }

}
