package com.intellias.intellistart.interviewplanning.dto;

import com.intellias.intellistart.interviewplanning.models.Booking;
import com.intellias.intellistart.interviewplanning.models.Interviewer;
import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Interviewer entity class.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class InterviewerDto {

  @NotNull
  private final Integer bookingLimit;
  @NotEmpty
  private final InterviewerSlot interviewerSlot;
  @NotEmpty
  private final Booking booking;

  /**
   * Quick DTO creation.

   * @param interviewer entity
   * @return DTO object
   */
  public static InterviewerDto of(Interviewer interviewer) {
    return new InterviewerDto(
      interviewer.getBookingLimit(),
      interviewer.getInterviewerSlot(),
      interviewer.getBooking()
    );
  }

}
