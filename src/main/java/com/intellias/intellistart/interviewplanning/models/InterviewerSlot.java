package com.intellias.intellistart.interviewplanning.models;

import com.intellias.intellistart.interviewplanning.models.enums.Status;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class InterviewerSlot {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "weekNum", nullable = false)
  private int weekNum;

  @Column(name = "dayOfWeek", nullable = false)
  private int dayOfWeek;

  @Column(name = "from", nullable = false)
  private LocalTime from;

  @Column(name = "to", nullable = false)
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
