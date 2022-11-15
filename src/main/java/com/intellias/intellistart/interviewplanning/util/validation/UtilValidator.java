package com.intellias.intellistart.interviewplanning.util.validation;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Utility validator class for validating the simple, not related to a certain entity logic.
 */
public class UtilValidator {

  /**
   * Private constructor to hide the ability of instantiation of a utility class.
   */
  private UtilValidator() {
  }

  /**
   * Method that checks are the given time boundaries (time from and time to) are valid (are they
   * correspond to the functional requirements).
   *
   * @param from LocalTime from boundary
   * @param to   LocalTime to boundary
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

  /**
   * Checks if two intervals overlap.
   */
  public static boolean areIntervalsOverLapping(LocalDateTime from1, LocalDateTime to1,
      LocalDateTime from2, LocalDateTime to2) {
    return isTimeInInterval(from1, from2, to2)
        || isTimeInInterval(to1, from2, to2)
        || isTimeInInterval(from2, from1, to1)
        || isTimeInInterval(to2, from1, to1)
        || (from1.equals(from2) && to1.equals(to2));
  }

  /**
   * Checks if two intervals overlap.
   */
  public static boolean areIntervalsOverLapping(LocalTime from1, LocalTime to1,
      LocalTime from2, LocalTime to2) {
    return isTimeInInterval(from1, from2, to2)
        || isTimeInInterval(to1, from2, to2)
        || isTimeInInterval(from2, from1, to1)
        || isTimeInInterval(to2, from1, to1)
        || (from1.equals(from2) && to1.equals(to2));
  }

  private static boolean isTimeInInterval(LocalDateTime time, LocalDateTime intervalBegin,
      LocalDateTime intervalEnd) {
    return time.compareTo(intervalBegin) > 0
        && time.compareTo(intervalEnd) < 0;
  }

  private static boolean isTimeInInterval(LocalTime time, LocalTime intervalBegin,
      LocalTime intervalEnd) {
    return time.compareTo(intervalBegin) > 0
        && time.compareTo(intervalEnd) < 0;
  }
}
