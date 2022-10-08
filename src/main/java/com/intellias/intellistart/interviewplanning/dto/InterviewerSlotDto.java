package com.intellias.intellistart.interviewplanning.dto;

import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.models.enums.Status;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Interviewer slot entity class.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class InterviewerSlotDto implements Serializable {

  @NotNull
  private final Integer weekNum;
  @NotEmpty
  private final DayOfWeek dayOfWeek;
  @NotEmpty
  private final LocalTime from;
  @NotEmpty
  private final LocalTime to;
  @NotEmpty
  private final Status status;

  /**
   * Quick DTO creation.

   * @param slot entity
   * @return DTO object
   */
  public static InterviewerSlotDto of(InterviewerSlot slot) {
    return new InterviewerSlotDto(
      slot.getWeekNum(),
      slot.getDayOfWeek(),
      slot.getTimeFrom(),
      slot.getTimeTo(),
      slot.getStatus()
    );
  }

}
