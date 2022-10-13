package com.intellias.intellistart.interviewplanning.models;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@Table(name = "candidates")
public class Candidate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany
  @JoinColumn(name = "candidate_slot_id")
  private List<CandidateSlot> candidateSlot;

  @OneToMany
  @JoinColumn(name = "booking_id")
  private List<Booking> booking;

  /**
   * Candidate constructor.
   */

  public Candidate(List<CandidateSlot> candidateSlot, User user, List<Booking> booking) {
    this.candidateSlot = candidateSlot;
    this.user = user;
    this.booking = booking;
  }
}
