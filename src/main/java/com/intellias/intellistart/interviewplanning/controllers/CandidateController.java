package com.intellias.intellistart.interviewplanning.controllers;

import com.intellias.intellistart.interviewplanning.dto.TimeSlotDTO;
import com.intellias.intellistart.interviewplanning.services.InterviewerService;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CandidateController.MAPPING)
public class CandidateController {

  public static final String MAPPING = "/candidate";
  private final InterviewerService interviewerService;
  private final List<TimeSlotDTO> timeSlotDTOs = new ArrayList<>();

  @Autowired
  public CandidateController(InterviewerService interviewerService) {
    this.interviewerService = interviewerService;
  }

  @GetMapping("/addTimeSlot")
  public ResponseEntity<HttpStatus> add(){
    timeSlotDTOs.add(
        new TimeSlotDTO(1, DayOfWeek.MONDAY, LocalTime.of(9, 30), LocalTime.of(11, 0))
    );
    timeSlotDTOs.add(
        new TimeSlotDTO(1, DayOfWeek.MONDAY, LocalTime.of(11, 0), LocalTime.of(12, 30))
    );
    timeSlotDTOs.add(
        new TimeSlotDTO(1, DayOfWeek.MONDAY, LocalTime.of(12, 30), LocalTime.of(14, 0))
    );

    return ResponseEntity.ok(HttpStatus.OK);
  }

  @GetMapping("/getTimeSlots")
  public List<TimeSlotDTO> get(){
    return timeSlotDTOs;
  }


}
