package com.intellias.intellistart.interviewplanning.util;

import java.time.DayOfWeek;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Form for all data.
 */
@Getter
@Setter
@AllArgsConstructor
public class TimeSlotForm {

  /**
   * Builder for TimeSlotForm.
   */
  public static class TimeSlotFormBuilder {

    private int weekNum;
    private DayOfWeek day;
    private LocalTime from;
    private LocalTime to;

    public TimeSlotFormBuilder from(LocalTime time) {
      this.from = time;
      return this;
    }

    /**
     * Builder method for starting time.
     *
     * @param time starting time
     * @return builder object
     */
    public TimeSlotFormBuilder from(String time) {
      String[] timeSplit = time.split(":");
      return this.from(
          LocalTime.of(
              Integer.parseInt(timeSplit[0]),
              Integer.parseInt(timeSplit[1])
          )
      );
    }

    public TimeSlotFormBuilder to(LocalTime time) {
      this.to = time;
      return this;
    }

    /**
     * Builder method for ending time.
     *
     * @param time starting time
     * @return builder object
     */
    public TimeSlotFormBuilder to(String time) {
      String[] timeSplit = time.split(":");
      return this.to(
          LocalTime.of(
              Integer.parseInt(timeSplit[0]),
              Integer.parseInt(timeSplit[1])
          )
      );
    }

    public TimeSlotFormBuilder day(DayOfWeek day) {
      this.day = day;
      return this;
    }

    public TimeSlotFormBuilder day(String day) {
      return this.day(DayOfWeek.valueOf(day.toUpperCase()));
    }

    public TimeSlotFormBuilder week(int weenNum) {
      this.weekNum = weenNum;
      return this;
    }

    public TimeSlotForm build() {
      return new TimeSlotForm(weekNum, day, from, to);
    }

  }

  private int weekNum;
  private DayOfWeek day;
  private LocalTime from;
  private LocalTime to;

  public static TimeSlotFormBuilder builder() {
    return new TimeSlotFormBuilder();
  }

}
