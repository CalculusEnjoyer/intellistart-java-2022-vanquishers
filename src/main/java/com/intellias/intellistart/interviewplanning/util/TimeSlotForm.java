package com.intellias.intellistart.interviewplanning.util;

import java.time.DayOfWeek;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Form for all data.
 */
@Data
@AllArgsConstructor
public class TimeSlotForm {

  /**
   * Builder for TimeSlotForm.
   */
  public static class Builder {

    private int weekNum;
    private DayOfWeek day;
    private LocalTime from;
    private LocalTime to;

    public Builder from(LocalTime time) {
      this.from = time;
      return this;
    }

    /**
     * Builder method for starting time.

     * @param time starting time
     * @return this builder object
     */
    public Builder from(String time) {
      String[] timeSplit = time.split(":");
      return this.from(
          LocalTime.of(
              Integer.parseInt(timeSplit[0]),
              Integer.parseInt(timeSplit[1])
          )
      );
    }

    public Builder to(LocalTime time) {
      this.to = time;
      return this;
    }

    /**
     * Builder method for ending time.

     * @param time starting time
     * @return this builder object
     */
    public Builder to(String time) {
      String[] timeSplit = time.split(":");
      return this.to(
          LocalTime.of(
              Integer.parseInt(timeSplit[0]),
              Integer.parseInt(timeSplit[1])
          )
      );
    }

    public Builder day(DayOfWeek day) {
      this.day = day;
      return this;
    }

    public Builder day(String day) {
      return this.day(DayOfWeek.valueOf(day.toUpperCase()));
    }

    public Builder week(int weenNum) {
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

  public static Builder builder() {
    return new Builder();
  }

}
