package com.intellias.intellistart.interviewplanning.models;

import com.intellias.intellistart.interviewplanning.dto.CandidateDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Candidate entity class.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Candidate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "candidate_slot_id")
  private CandidateSlot candidateSlot;

  @ManyToOne
  @JoinColumn(name = "booking_id")
  private Booking booking;

  public Candidate(CandidateSlot candidateSlot, Booking booking) {
    this.candidateSlot = candidateSlot;
    this.booking = booking;
  }

  /**
   * Quick Entity creation.

   * @param dto DTO object
   * @return entity
   */
  public static Candidate of(CandidateDto dto) {
    return new Candidate(
        dto.getCandidateSlot(),
        dto.getBooking()
    );
  }

}
