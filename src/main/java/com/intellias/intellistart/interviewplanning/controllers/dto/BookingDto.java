package com.intellias.intellistart.interviewplanning.controllers.dto;

import com.intellias.intellistart.interviewplanning.models.enums.Status;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Booking class.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto implements Serializable {

  @NotEmpty
  private InterviewerSlotDto interviewerSlot;
  @NotEmpty
  private CandidateSlotDto candidateSlot;
  @NotEmpty
  private String subject;
  @NotEmpty
  private String description;
  @NotEmpty
  private Status status;
}
