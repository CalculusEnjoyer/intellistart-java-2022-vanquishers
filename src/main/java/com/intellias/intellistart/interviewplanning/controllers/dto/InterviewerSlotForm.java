package com.intellias.intellistart.interviewplanning.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

/**
 * Interviewer slot form, that is used in InterviewerController endpoints responses. Comparing to
 * the corresponding dto class, it also contains the slot id, instead of the interviewer id.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InterviewerSlotForm implements Serializable {

  private Integer weekNum;

  @NotNull
  @Range(min = 1, max = 7)
  private Integer dayOfWeek;

  @NotNull
  @JsonFormat(pattern = "HH:mm")
  private LocalTime timeFrom;

  @NotNull
  @JsonFormat(pattern = "HH:mm")
  private LocalTime timeTo;

  private Long id;

}
