package com.intellias.intellistart.interviewplanning.models;

import com.intellias.intellistart.interviewplanning.models.enums.Status;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * InterviewerSlot class.
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "interviewer_slots")
public class InterviewerSlot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "week_num", nullable = false)
  private int weekNum;

  @Column(name = "day_of_week", nullable = false)
  private int dayOfWeek;

  @Column(name = "time_from", nullable = false)
  private LocalTime from;

  @Column(name = "time_to", nullable = false)
  private LocalTime to;

  @Column(name = "status", nullable = false)
  private Status status;

  /**
   * InterviewerSlot constructor.
   */
  public InterviewerSlot(int weekNum, int dayOfWeek, LocalTime from, LocalTime to, Status status) {
    this.weekNum = weekNum;
    this.dayOfWeek = dayOfWeek;
    this.from = from;
    this.to = to;
    this.status = status;
  }
}
