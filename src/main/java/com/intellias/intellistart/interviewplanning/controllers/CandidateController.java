package com.intellias.intellistart.interviewplanning.controllers;

import com.intellias.intellistart.interviewplanning.dto.TimeSlotDto;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Candidate test controller.
 */
@RestController
@RequestMapping(CandidateController.MAPPING)
public class CandidateController {

  public static final String MAPPING = "/candidate";
  private final List<TimeSlotDto> timeSlotDtos = new ArrayList<>();

  /**
   * Method for test request generating time slots.

   * @return response status
   */
  @GetMapping("/addTimeSlots")
  public ResponseEntity<HttpStatus> addTimeSlots() {
    timeSlotDtos.add(
        TimeSlotDto.of(1, DayOfWeek.MONDAY, LocalTime.of(9, 30), LocalTime.of(11, 0))
    );
    timeSlotDtos.add(
        TimeSlotDto.of(1, DayOfWeek.MONDAY, LocalTime.of(11, 0), LocalTime.of(12, 30))
    );
    timeSlotDtos.add(
        TimeSlotDto.of(1, DayOfWeek.MONDAY, LocalTime.of(12, 30), LocalTime.of(14, 0))
    );

    return ResponseEntity.ok(HttpStatus.OK);
  }

  @GetMapping("/getTimeSlots")
  public List<TimeSlotDto> getTimeSlots() {
    return timeSlotDtos;
  }


}
