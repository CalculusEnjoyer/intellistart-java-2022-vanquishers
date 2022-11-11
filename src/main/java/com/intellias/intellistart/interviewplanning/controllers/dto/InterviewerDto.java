package com.intellias.intellistart.interviewplanning.controllers.dto;

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
  private UserDto user;

  @NotEmpty
  private Integer bookingLimit;

}
