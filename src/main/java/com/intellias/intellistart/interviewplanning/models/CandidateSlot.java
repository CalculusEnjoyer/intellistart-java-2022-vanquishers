package com.intellias.intellistart.interviewplanning.models;

import java.time.LocalDateTime;
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
 * Candidate time slot class.
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
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

  @ManyToOne
  @JoinColumn(name = "candidate_id")
  private Candidate candidate;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "candidateSlot", cascade = CascadeType.PERSIST)
  private Set<Booking> booking = new HashSet<>();

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
