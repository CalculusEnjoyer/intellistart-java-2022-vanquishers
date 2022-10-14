package com.intellias.intellistart.interviewplanning.controllers.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Candidate slot class.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CandidateSlotDto implements Serializable {

  @NotEmpty
  private LocalDateTime dateFrom;
  @NotEmpty
  private LocalDateTime dateTo;

}