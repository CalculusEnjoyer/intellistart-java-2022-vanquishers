package com.intellias.intellistart.interviewplanning.models;

import com.intellias.intellistart.interviewplanning.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.models.enums.Status;
import java.time.DayOfWeek;
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
  private Integer weekNum;

  @Column(name = "dayOfWeek", nullable = false)
  private DayOfWeek dayOfWeek;

  @Column(name = "timeFrom", nullable = false)
  private LocalTime timeFrom;

  @Column(name = "timeTo", nullable = false)
  private LocalTime timeTo;

  @Column(name = "status", nullable = false)
  private Status status;

  /**
   * InterviewerSlot constructor.
   */
  public InterviewerSlot(int weekNum, DayOfWeek dayOfWeek,
      LocalTime from, LocalTime to, Status status) {
    this.weekNum = weekNum;
    this.dayOfWeek = dayOfWeek;
    this.timeFrom = from;
    this.timeTo = to;
    this.status = status;
  }

  /**
   * Quick Entity creation.

   * @param dto DTO object
   * @return entity
   */
  public static InterviewerSlot of(InterviewerSlotDto dto) {
    return new InterviewerSlot(
        dto.getWeekNum(),
        dto.getDayOfWeek(),
        dto.getFrom(),
        dto.getTo(),
        dto.getStatus()
    );
  }

}
