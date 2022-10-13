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
public class Interviewer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany
  @JoinColumn(name = "interviewer_slot_id")
  private List<InterviewerSlot> interviewerSlot;


  @Column(name = "booking_limit", nullable = false)
  private int bookingLimit;

  @OneToMany
  @JoinColumn(name = "booking_id")
  private List<Booking> booking;

  /**
   * Interviewer constructor.
   */
  public Interviewer(User user, int bookingLimit, List<InterviewerSlot> interviewerSlot,
      List<Booking> booking) {
    this.user = user;
    this.bookingLimit = bookingLimit;
    this.interviewerSlot = interviewerSlot;
    this.booking = booking;
  }

}
