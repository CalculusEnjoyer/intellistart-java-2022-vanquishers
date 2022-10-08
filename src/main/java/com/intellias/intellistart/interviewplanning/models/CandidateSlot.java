package com.intellias.intellistart.interviewplanning.models;

import com.intellias.intellistart.interviewplanning.dto.CandidateSlotDto;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Candidate time slot class.
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
public class CandidateSlot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "dateFrom", nullable = false)
  private LocalDateTime dateFrom;

  @Column(name = "dateTo", nullable = false)
  private LocalDateTime dateTo;

  public CandidateSlot(LocalDateTime dateFrom, LocalDateTime dateTo) {
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
  }

  /**
   * Quick Entity creation.

   * @param dto DTO object
   * @return entity
   */
  public static CandidateSlot of(CandidateSlotDto dto) {
    return new CandidateSlot(
        dto.getDateFrom(),
        dto.getDateTo()
    );
  }

}
