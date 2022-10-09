package com.intellias.intellistart.interviewplanning.controllers.dto;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Candidate class.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDto {

  @NotEmpty
  private CandidateSlotDto candidateSlot;
  @NotEmpty
  private BookingDto booking;

}
