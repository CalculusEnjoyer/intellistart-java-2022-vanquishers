package com.intellias.intellistart.interviewplanning.models;

import com.intellias.intellistart.interviewplanning.models.enums.Status;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@Table(name = "bookings")
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

  @Column(name = "time_from", nullable = false)
  private LocalDateTime from;

  @Column(name = "time_to", nullable = false)
  private LocalDateTime to;

  @Column(name = "subject", nullable = false)
  private String subject;

  @Column(name = "description")
  private String description;

  @Column(name = "status", nullable = false)
  private Status status;

  /**
   * Booking constructor.
   */
  public Booking(InterviewerSlot interviewerSlot, CandidateSlot candidateSlot, LocalDateTime from,
      LocalDateTime to, String subject, String description, Status status) {
    this.interviewerSlot = interviewerSlot;
    this.candidateSlot = candidateSlot;
    this.from = from;
    this.to = to;
    this.subject = subject;
    this.description = description;
    this.status = status;
  }

}
