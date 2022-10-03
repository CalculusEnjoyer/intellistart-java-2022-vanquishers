package com.intellias.intellistart.interviewplanning.dto;


import com.intellias.intellistart.interviewplanning.models.TimeSlot;
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

}