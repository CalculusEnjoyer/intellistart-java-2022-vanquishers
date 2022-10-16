package com.intellias.intellistart.interviewplanning.controllers.dto;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
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
  private LocalTime timeFrom;
  @NotEmpty
  private LocalTime timeTo;
  private Set<BookingDto> booking = new HashSet<>();

}
