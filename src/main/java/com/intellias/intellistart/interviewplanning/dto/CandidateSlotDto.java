package com.intellias.intellistart.interviewplanning.dto;

import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Candidate slot entity class.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CandidateSlotDto implements Serializable {

  @NotEmpty
  private final LocalDateTime dateFrom;
  @NotEmpty
  private final LocalDateTime dateTo;

  /**
   * Quick DTO creation.

   * @param slot entity
   * @return DTO object
   */
  public static CandidateSlotDto of(CandidateSlot slot) {
    return new CandidateSlotDto(
      slot.getDateFrom(),
      slot.getDateTo()
    );
  }

}
