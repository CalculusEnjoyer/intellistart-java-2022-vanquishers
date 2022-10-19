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
   * Method for getting the current week number in current year.

   * @return week number
   */
  public int getCurrentWeekNumInYear() {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setFirstDayOfWeek(Calendar.MONDAY);
    calendar.setMinimalDaysInFirstWeek(1);
    return calendar.get(Calendar.WEEK_OF_YEAR);
  }

  /**
   * Method for getting the current week number with current year (202243).

   * @return formatted week number
   */
  public int getCurrentWeekNumInFormat() {
    return Integer.parseInt(String.valueOf(LocalDate.now().getYear()) + getCurrentWeekNumInYear());
  }

  /**
   * Method for getting the next week number with current year (202244).

   * @return formatted week number
   */
  public int getNextWeekNumInFormat() {
    return Integer
        .parseInt(String.valueOf(LocalDate.now().getYear()) + (getCurrentWeekNumInYear() + 1));
  }
}
