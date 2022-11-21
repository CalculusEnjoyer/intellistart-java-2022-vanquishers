package com.intellias.intellistart.interviewplanning.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.intellias.intellistart.interviewplanning.models.enums.Status;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Booking form class that additionally contains booking primary key.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookingForm implements Serializable {

  @NotEmpty
  private Long id;
  @NotEmpty
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime dateFrom;
  @NotEmpty
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime dateTo;
  @NotEmpty
  private String subject;
  @NotEmpty
  private String description;
  private Status status;
  @NotEmpty
  private Long candidateSlotId;
  @NotEmpty
  private Long interviewerSlotId;
}
