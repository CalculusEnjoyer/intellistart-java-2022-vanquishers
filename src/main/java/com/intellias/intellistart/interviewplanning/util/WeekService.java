package com.intellias.intellistart.interviewplanning.util;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.springframework.stereotype.Service;

/**
 * Week service.
 */
@Service
public class WeekService {

  /**
   * Method for getting calendar for current week.
   *
   * @return calendar
   */
  public GregorianCalendar getCalendar() {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setFirstDayOfWeek(Calendar.MONDAY);
    calendar.setMinimalDaysInFirstWeek(7);
    return calendar;
  }

  /**
   * Method for getting calendar for next week.
   *
   * @return calendar
   */
  public GregorianCalendar getNextWeekCalendar() {
    GregorianCalendar calendar = getCalendar();
    LocalDate dayOnNextWeek = LocalDate.now().plusDays(7);
    calendar.set(dayOnNextWeek.getYear(), dayOnNextWeek.getMonthValue() - 1,
        dayOnNextWeek.getDayOfMonth());
    return calendar;
  }

  /**
   * Method for getting the current week number with current year (202243).
   *
   * @return formatted week number
   */
  public int getCurrentWeekNumInFormat() {
    GregorianCalendar calendar = getCalendar();
    return formatWeek(calendar.get(Calendar.YEAR), calendar.get(Calendar.WEEK_OF_YEAR));
  }

  /**
   * Method for getting the next week number with current year (202244).
   *
   * @return formatted week number
   */
  public int getNextWeekNumInFormat() {
    GregorianCalendar calendar = getNextWeekCalendar();
    return formatWeek(calendar.get(Calendar.YEAR), calendar.get(Calendar.WEEK_OF_YEAR));
  }

  /**
   * Method for getting week in format (02).
   *
   * @return week
   */
  private int formatWeek(int year, int week) {
    return Integer.parseInt(year + String.format("%02d", week));
  }
}
