package com.intellias.intellistart.interviewplanning.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime dateFrom;
  @NotEmpty
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime dateTo;
  @NotEmpty
  private Long candidateId;
}
