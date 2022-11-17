package com.intellias.intellistart.interviewplanning.models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "candidate", cascade = CascadeType.PERSIST)
  private Set<CandidateSlot> candidateSlot = new HashSet<>();

  /**
   * Candidate constructor.
   */

  public Candidate(Set<CandidateSlot> candidateSlot, User user) {
    this.candidateSlot = candidateSlot;
    this.user = user;
  }

}
