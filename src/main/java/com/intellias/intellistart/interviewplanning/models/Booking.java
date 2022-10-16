package com.intellias.intellistart.interviewplanning.models;

import com.intellias.intellistart.interviewplanning.models.enums.Status;
import java.time.LocalDateTime;
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

  @Column(name = "t_from", nullable = false)
  private LocalDateTime from;

  @Column(name = "t_to", nullable = false)
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
  public Booking(LocalDateTime from,
      LocalDateTime to, String subject, String description, Status status) {
    this.from = from;
    this.to = to;
    this.subject = subject;
    this.description = description;
    this.status = status;
  }

}
