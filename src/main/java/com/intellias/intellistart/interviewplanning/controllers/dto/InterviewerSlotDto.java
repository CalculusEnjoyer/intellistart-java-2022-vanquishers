package com.intellias.intellistart.interviewplanning.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalTime;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
  private Integer dayOfWeek;
  @NotEmpty
  @JsonFormat(pattern = "HH:mm")
  private LocalTime timeFrom;
  @NotEmpty
  @JsonFormat(pattern = "HH:mm")
  private LocalTime timeTo;

}
