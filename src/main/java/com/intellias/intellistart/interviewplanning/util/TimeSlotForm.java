package com.intellias.intellistart.interviewplanning.util;

import java.time.DayOfWeek;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeSlotForm {

  public static class Builder {

    private int weekNum;
    private DayOfWeek day;
    private LocalTime from;
    private LocalTime to;

    public Builder week(int weekNum) {
      this.weekNum = weekNum;
      return this;
    }

    public Builder day(String day) {
      return this.day(DayOfWeek.valueOf(day.toUpperCase()));
    }

    public Builder day(DayOfWeek day) {
      this.day = day;
      return this;
    }

    public Builder from(String time) {
      this.from = LocalTime.parse(time);
      return this;
    }

    public Builder to(String time) {
      this.to = LocalTime.parse(time);
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
