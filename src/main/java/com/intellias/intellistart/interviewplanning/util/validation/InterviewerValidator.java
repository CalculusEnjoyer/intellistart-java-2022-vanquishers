package com.intellias.intellistart.interviewplanning.util.validation;

import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.util.exceptions.InterviewApplicationException;
import com.intellias.intellistart.interviewplanning.util.exceptions.InvalidSlotBoundariesException;
import java.time.LocalTime;
import org.springframework.http.HttpStatus;

/**
 * Validator for interviewers and interviewer-related data.
 */
public class InterviewerValidator {

  /**
   * Private constructor to hide the ability of instantiation of a utility class.
   */
  private InterviewerValidator() {
  }

  /**
   * Method that validates the maximum amount of bookings. It should be a positive integer.
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

  public static void validateSlot(InterviewerSlot slot) {
    validateDuration(slot.getFrom(), slot.getTo());
  }

  private static void validateDuration(LocalTime from, LocalTime to) {
    if (!UtilValidator.isValidTimeBoundaries(from, to)) {
      throw new InvalidSlotBoundariesException("Invalid slot time boundaries");
    }
  }
}
