package com.intellias.intellistart.interviewplanning.controllers.dto;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Interviewer class.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InterviewerDto {

  @NotEmpty
  private Integer bookingLimit;
  private Set<InterviewerSlotDto> interviewerSlot = new HashSet<>();

}
