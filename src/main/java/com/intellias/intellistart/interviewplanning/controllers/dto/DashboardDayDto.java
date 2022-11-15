package com.intellias.intellistart.interviewplanning.controllers.dto;

import java.io.Serializable;
import java.time.LocalDate;
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
public class DashboardDayDto implements Serializable {

  private LocalDate day;
  private List<InterviewerSlotFormWithBookingIds> interviewerSlotFormsWithId = new ArrayList<>();
  private List<CandidateSlotFormWithBookingIds> candidateSlotsFormsWithId = new ArrayList<>();
  private Map<Long, BookingDto> bookings = new HashMap<>();

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
  public static class InterviewerSlotFormWithBookingIds implements Serializable {

    private InterviewerSlotDto interviewerSlotDto;
    private List<Long> bookingsId = new ArrayList<>();
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
  public static class CandidateSlotFormWithBookingIds implements Serializable {

    private CandidateSlotDto candidateSlotDto;
    private List<Long> bookingsId = new ArrayList<>();
  }
}
