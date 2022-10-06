package com.intellias.intellistart.interviewplanning.models;

import com.sun.istack.NotNull;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "time_slot")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlot implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "week_num")
  private int weekNum;

  @NotEmpty
  @Column(name = "day_of_week")
  private DayOfWeek dayOfWeek;

  @NotEmpty
  @Column(name = "from")
  private LocalTime from;

  @NotEmpty
  @Column(name = "to")
  private LocalTime to;
}
