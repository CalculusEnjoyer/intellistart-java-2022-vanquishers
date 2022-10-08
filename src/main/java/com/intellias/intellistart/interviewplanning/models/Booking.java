package com.intellias.intellistart.interviewplanning.models;

import com.intellias.intellistart.interviewplanning.dto.BookingDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Booking entity class.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @OneToOne
  @JoinColumn(name = "interviewer_slot_id", nullable = false)
  private InterviewerSlot interviewerSlot;

  @OneToOne
  @JoinColumn(name = "candidate_slot_id", nullable = false)
  private CandidateSlot candidateSlot;

  @Column(name = "info")
  private String info;

  /**
   * Booking constructor.
   */
  public Booking(InterviewerSlot interviewerSlot, CandidateSlot candidateSlot, String info) {
    this.interviewerSlot = interviewerSlot;
    this.candidateSlot = candidateSlot;
    this.info = info;
  }

  /**
   * Quick Entity creation.

   * @param dto DTO object
   * @return entity
   */
  public static Booking of(BookingDto dto) {
    return new Booking(
        dto.getInterviewerSlot(),
        dto.getCandidateSlot(),
        dto.getInfo()
    );
  }

}
