package com.intellias.intellistart.interviewplanning.dto;


import com.intellias.intellistart.interviewplanning.models.TimeSlot;
import com.intellias.intellistart.interviewplanning.util.TimeSlotForm;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * A DTO for the {@link TimeSlot} entity.
 */
@Data
public class TimeSlotDto implements Serializable {

  @NotNull
  private final int weekNum;
  @NotEmpty
  private final DayOfWeek dayOfWeek;
  @NotEmpty
  private final LocalTime from;
  @NotEmpty
  private final LocalTime to;

  public static TimeSlotDto of(int weekNum, DayOfWeek dayOfWeek, LocalTime from, LocalTime to) {
    return new TimeSlotDto(weekNum, dayOfWeek, from, to);
  }

  /**
   * Quick creation method for TimeSlotDto from TimeSlotForm.

   * @param timeSlotForm TimeSlotForm data
   * @return TimeSlotDto object
   */
  public static TimeSlotDto of(TimeSlotForm timeSlotForm) {
    return new TimeSlotDto(
        timeSlotForm.getWeekNum(),
        timeSlotForm.getDay(),
        timeSlotForm.getFrom(),
        timeSlotForm.getTo()
    );
  }

}
