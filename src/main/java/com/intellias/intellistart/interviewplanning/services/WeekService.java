package com.intellias.intellistart.interviewplanning.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.IsoFields;
import org.springframework.stereotype.Service;

/**
 * Week service.
 */
@Service
public class WeekService {
  public static final ZoneId ZONE_ID = ZoneId.of("Europe/Kiev");

  /**
   * Method for getting the current week number with current year (202243).
   *
   * @return formatted current week number
   */
  public int getCurrentWeekNum() {
    return getWeekNumFrom(getCurrentDate());
  }

  /**
   * Method for getting the next week number with current year (202244).
   *
   * @return formatted next week number
   */
  public int getNextWeekNum() {
    return getWeekNumFrom(getCurrentDate().plusDays(7));
  }

  /**
   * Method for getting week in format (02).
   *
   * @return formatted week number for a given date
   */
  public int getWeekNumFrom(LocalDate date) {
    int year = date.get(IsoFields.WEEK_BASED_YEAR);
    int week = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
    return Integer.parseInt(year + String.format("%02d", week));
  }

  /**
   * Method for getting current date in specified in ZONE_ID timezone.
   *
   * @return LocalDate instance with current date for ZONE_ID timezone
   */
  private LocalDate getCurrentDate() {
    return LocalDate.now(ZONE_ID);
  }
}
