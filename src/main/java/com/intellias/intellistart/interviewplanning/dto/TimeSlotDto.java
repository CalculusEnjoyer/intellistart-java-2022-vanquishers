package com.intellias.intellistart.interviewplanning.dto;

import com.sun.istack.NotNull;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * A DTO for the {@link com.intellias.intellistart.interviewplanning.models.TimeSlot} entity
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