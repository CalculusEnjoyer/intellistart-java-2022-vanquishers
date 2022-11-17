package com.intellias.intellistart.interviewplanning.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * Interviewer entity class.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "interviewers")
public class Interviewer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "booking_limit", nullable = false)
  private int bookingLimit;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  @JsonIgnore
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "interviewer", cascade = CascadeType.PERSIST)
  private Set<InterviewerSlot> interviewerSlot = new HashSet<>();

  /**
   * Interviewer constructor.
   */
  public Interviewer(User user, int bookingLimit, Set<InterviewerSlot> interviewerSlot) {
    this.user = user;
    this.bookingLimit = bookingLimit;
    this.interviewerSlot = interviewerSlot;
  }
}
