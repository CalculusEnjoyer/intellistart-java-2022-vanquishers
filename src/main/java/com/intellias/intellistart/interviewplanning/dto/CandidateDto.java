package com.intellias.intellistart.interviewplanning.dto;

import com.intellias.intellistart.interviewplanning.models.Booking;
import com.intellias.intellistart.interviewplanning.models.Candidate;
import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Candidate entity class.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CandidateDto {

  @NotEmpty
  private final CandidateSlot candidateSlot;
  @NotEmpty
  private final Booking booking;

  /**
   * Quick DTO creation.

   * @param candidate entity
   * @return DTO object
   */
  public static CandidateDto of(Candidate candidate) {
    return new CandidateDto(
      candidate.getCandidateSlot(),
      candidate.getBooking()
    );
  }

}
