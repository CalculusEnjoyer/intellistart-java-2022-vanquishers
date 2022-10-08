package com.intellias.intellistart.interviewplanning.dto;

import com.intellias.intellistart.interviewplanning.models.Booking;
import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Booking entity class.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BookingDto {

  @NotEmpty
  private final InterviewerSlot interviewerSlot;
  @NotEmpty
  private final CandidateSlot candidateSlot;
  @NotEmpty
  private final String info;

  /**
   * Quick DTO creation.

   * @param booking entity
   * @return DTO object
   */
  public static BookingDto of(Booking booking) {
    return new BookingDto(
      booking.getInterviewerSlot(),
      booking.getCandidateSlot(),
      booking.getInfo()
    );
  }

}
