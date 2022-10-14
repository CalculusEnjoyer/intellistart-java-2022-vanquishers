package com.intellias.intellistart.interviewplanning.models;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

/**
 * Candidate time slot class.
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "candidate_slots")
public class CandidateSlot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "date_from", nullable = false)
  private LocalDateTime dateFrom;

  @Column(name = "date_to", nullable = false)
  private LocalDateTime dateTo;

  public CandidateSlot(LocalDateTime dateFrom, LocalDateTime dateTo) {
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    CandidateSlot that = (CandidateSlot) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
