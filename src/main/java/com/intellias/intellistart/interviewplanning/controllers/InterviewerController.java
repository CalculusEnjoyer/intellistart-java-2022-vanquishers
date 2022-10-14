package com.intellias.intellistart.interviewplanning.controllers;

import com.intellias.intellistart.interviewplanning.controllers.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.services.InterviewerService;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Interviewer controller.
 */
@RestController
@RequestMapping("/interviewers")
public class InterviewerController {

  public final InterviewerService interviewerService;
  public final ModelMapper mapper;

  @Autowired
  public InterviewerController(InterviewerService interviewerService, ModelMapper mapper) {
    this.interviewerService = interviewerService;
    this.mapper = mapper;
  }

  /**
   * Test generating of interviewer time slots.
   * /interviewers/addSlots

   * @return string status
   */
  @PostMapping("/addSlots")
  public ResponseEntity<HttpStatus> addSlots() {
    List<InterviewerSlot> slots = new ArrayList<>(
        Arrays.asList(
            new InterviewerSlot(0, 1,
                LocalTime.of(9, 30), LocalTime.of(11, 0)),

            new InterviewerSlot(2, 2,
                LocalTime.of(9, 30), LocalTime.of(11, 0)),

            new InterviewerSlot(1, 3,
                LocalTime.of(9, 30), LocalTime.of(11, 0)),

            new InterviewerSlot(1, 4,
                LocalTime.of(9, 30), LocalTime.of(11, 0))
        )
    );
    interviewerService.registerAll(slots);

    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Test getting of interviewer time slots.
   * /interviewers/getSlots

   * @return list of interviewer slots in DB
   */
  @GetMapping("/getSlots")
  public List<InterviewerSlotDto> getSlots() {
    return interviewerService.findAll().stream()
        .map(e -> mapper.map(e, InterviewerSlotDto.class))
        .collect(Collectors.toList());
  }

  /**
   * Test deleting of interviewer time slots.
   * /interviewers/delSlots

   * @return list of interviewer slots in DB
   */
  @GetMapping("/delSlots")
  public List<InterviewerSlotDto> delSlots() {
    interviewerService.deleteAll();
    return interviewerService.findAll().stream()
        .map(e -> mapper.map(e, InterviewerSlotDto.class))
        .collect(Collectors.toList());
  }

}
