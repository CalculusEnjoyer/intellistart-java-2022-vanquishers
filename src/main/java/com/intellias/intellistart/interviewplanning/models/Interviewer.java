package com.intellias.intellistart.interviewplanning.models;

import com.intellias.intellistart.interviewplanning.dto.InterviewerDto;
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

  @Column(name = "bookingLimit", nullable = false)
  private Integer bookingLimit;

  @ManyToOne
  @JoinColumn(name = "interviewer_slot_id")
  private InterviewerSlot interviewerSlot;

  @ManyToOne
  @JoinColumn(name = "booking_id")
  private Booking booking;

  /**
   * Interviewer constructor.
   */
  public Interviewer(int bookingLimit, InterviewerSlot interviewerSlot, Booking booking) {
    this.bookingLimit = bookingLimit;
    this.interviewerSlot = interviewerSlot;
    this.booking = booking;
  }

  /**
   * Quick Entity creation.

   * @param dto DTO object
   * @return entity
   */
  public static Interviewer of(InterviewerDto dto) {
    return new Interviewer(
        dto.getBookingLimit(),
        dto.getInterviewerSlot(),
        dto.getBooking()
    );
  }

}
