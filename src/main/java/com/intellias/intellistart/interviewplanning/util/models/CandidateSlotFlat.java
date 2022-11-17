package com.intellias.intellistart.interviewplanning.util.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *  Utility class, only used for mapping
 *  CandidateSlot to non-recursive object
 *  for user output request.
 */
@Getter
@Setter
@ToString
public class CandidateSlotFlat {

  @NotEmpty
  private Long id;
  @NotEmpty
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime dateFrom;
  @NotEmpty
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime dateTo;

  /**
   * Constructor from CandidateSlot.

   * @param slot slot detailed information
   */
  public CandidateSlotFlat(CandidateSlot slot) {
    id = slot.getId();
    dateFrom = slot.getDateFrom();
    dateTo = slot.getDateTo();
  }

}
