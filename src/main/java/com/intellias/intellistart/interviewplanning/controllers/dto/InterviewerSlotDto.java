package com.intellias.intellistart.interviewplanning.controllers.dto;

import com.intellias.intellistart.interviewplanning.models.enums.Status;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Interviewer slot class.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InterviewerSlotDto implements Serializable {

  @NotEmpty
  private Integer weekNum;
  @NotEmpty
  private DayOfWeek dayOfWeek;
  @NotEmpty
  private LocalTime timeFrom;
  @NotEmpty
  private LocalTime timeTo;
  @NotEmpty
  private Status status;

}
