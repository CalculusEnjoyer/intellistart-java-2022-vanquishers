package com.intellias.intellistart.interviewplanning.controllers.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Day form class that is used for generating dashboard.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DayForm implements Serializable {

  Integer day;
  List<InterviewerSlotFormWithId> interviewerSlotFormsWithId = new ArrayList<>();
  List<CandidateSlotFormWithId> candidateSlotsFormsWithId = new ArrayList<>();
  Map<Long, BookingDto> bookings = new HashMap<>();

  /**
   * Stores InterviewerSlotDto and Bookings IDs attached to it. Created for easy serialisation in
   * JSON (because according to functional requirements, slots in a week dashboard have to be
   * displayed with list of Bookings IDs that is attached to them).
   */
  @Getter
  @Setter
  @ToString
  @AllArgsConstructor
  @NoArgsConstructor
  public static class InterviewerSlotFormWithId implements Serializable {

    InterviewerSlotDto interviewerSlotDto;
    List<Long> bookingsId = new ArrayList<>();
  }

  /**
   * Stores InterviewerSlotDto and Bookings IDs attached to it. Created for easy serialisation in
   * JSON (because according to functional requirements, slots in a week dashboard have to be
   * displayed with list of Bookings IDs that is attached to them).
   */
  @Getter
  @Setter
  @ToString
  @AllArgsConstructor
  @NoArgsConstructor
  public static class CandidateSlotFormWithId implements Serializable {

    CandidateSlotDto candidateSlotDto;
    List<Long> bookingsId = new ArrayList<>();
  }
}
