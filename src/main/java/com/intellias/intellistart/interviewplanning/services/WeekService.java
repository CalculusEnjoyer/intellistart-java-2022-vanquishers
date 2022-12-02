package com.intellias.intellistart.interviewplanning.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.IsoFields;
import java.time.temporal.WeekFields;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Week service.
 */
@Service
@Component
public class WeekService {

  private ZoneId zoneId;

  @Value("${current.timezone}")
  public void setZoneFromConfig(String zone) {
    zoneId = ZoneId.of(zone);
  }

  public ZoneId getZoneId() {
    return zoneId;
  }

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
   * Converts from weekNum + dayOfWeek format to LocalDate.
   */
  public LocalDate getDateFromWeekAndDay(int weekNum, int dayOfWeek) {
    String weekNumInString = String.valueOf(weekNum);
    int year = Integer.parseInt(weekNumInString.substring(0, 4));
    int weekNumWithoutYear = Integer.parseInt(weekNumInString.substring(4));
    return getCurrentDate()
        .with(WeekFields.ISO.weekBasedYear(), year)
        .with(WeekFields.ISO.weekOfWeekBasedYear(), weekNumWithoutYear)
        .with(WeekFields.ISO.dayOfWeek(), dayOfWeek);
  }

  /**
   * Method for getting the current day of week.
   */
  public int getCurrentDayOfWeek() {
    return getDayOfWeekFrom(getCurrentDate());
  }

  /**
   * Method for calculating day of week of an input date.
   */
  public int getDayOfWeekFrom(LocalDate date) {
    return date.getDayOfWeek().getValue();
  }

  /**
   * Method for getting current date in specified in ZONE_ID timezone.
   *
   * @return LocalDate instance with current date for ZONE_ID timezone
   */
  private LocalDate getCurrentDate() {
    return LocalDate.now(zoneId);
  }

}

