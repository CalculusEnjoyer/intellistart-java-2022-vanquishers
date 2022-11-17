package com.intellias.intellistart.interviewplanning.models;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

/**
 * InterviewerSlot class.
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

  @Column(name = "t_from", nullable = false)
  private LocalTime from;

  @Column(name = "t_to", nullable = false)
  private LocalTime to;

  @ManyToOne
  @JoinColumn(name = "interviewer_id")
  private Interviewer interviewer;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "interviewerSlot", cascade = CascadeType.PERSIST)
  private Set<Booking> booking = new HashSet<>();

  /**
   * InterviewerSlot constructor.
   */
  public InterviewerSlot(int weekNum, int dayOfWeek, LocalTime from, LocalTime to) {
    this.weekNum = weekNum;
    this.dayOfWeek = dayOfWeek;
    this.from = from;
    this.to = to;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    InterviewerSlot slot = (InterviewerSlot) o;
    return id != null && Objects.equals(id, slot.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
