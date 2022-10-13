package com.intellias.intellistart.interviewplanning.controllers.dto;

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
  @NotEmpty
  private InterviewerSlotDto interviewerSlot;
  @NotEmpty
  private BookingDto booking;

}
