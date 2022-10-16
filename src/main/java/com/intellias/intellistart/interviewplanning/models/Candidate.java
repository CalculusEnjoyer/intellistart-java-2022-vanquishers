package com.intellias.intellistart.interviewplanning.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
  @JoinColumn(name = "candidate_id")
  private Set<CandidateSlot> candidateSlot = new HashSet<>();

  @OneToMany
  @JoinColumn(name = "candidate_id")
  private Set<Booking> booking = new HashSet<>();

  /**
   * Candidate constructor.
   */

  public Candidate(Set<CandidateSlot> candidateSlot, User user, Set<Booking> booking) {
    this.candidateSlot = candidateSlot;
    this.user = user;
    this.booking = booking;
  }

}
