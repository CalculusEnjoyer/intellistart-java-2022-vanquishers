package com.intellias.intellistart.interviewplanning.controllers.dto;

import com.intellias.intellistart.interviewplanning.models.User;
import java.io.Serializable;
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
public class InterviewerDto implements Serializable {

  @NotEmpty
  private User user;

  @NotEmpty
  private Integer bookingLimit;

}
