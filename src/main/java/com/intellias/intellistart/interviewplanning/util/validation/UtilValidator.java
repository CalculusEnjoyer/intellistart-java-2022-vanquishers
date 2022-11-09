package com.intellias.intellistart.interviewplanning.util.validation;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Utility validator class for validating the simple, not related to a certain entity logic.
 */
public class UtilValidator {
  /**
   * Private constructor to hide the ability of instantiation of a utility class.
   */
  private UtilValidator() {}

  /**
   * Method that checks are the given time boundaries (time from and time to)
   * are valid (are they correspond to the functional requirements).

   * @param from LocalTime from boundary
   * @param to LocalTime to boundary
   * @return boolean, are the given time boundaries valid
   */
  public static boolean isValidTimeBoundaries(LocalTime from, LocalTime to) {
    return Duration.between(from, to).toMinutes() >= 90
        && isValidTimeBoundary(from)
        && isValidTimeBoundary(to)
        && to.getHour() > from.getHour();
  }

  private static boolean isValidTimeBoundary(LocalTime time) {
    return time.getMinute() % 30 == 0
        && time.getHour() >= 8 && time.getHour() <= 22;
  }
}
