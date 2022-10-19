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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Interviewer controller.
 */
@RestController
@RequestMapping(InterviewerController.MAPPING)
public class InterviewerController {
  public static final String MAPPING = "/interviewers";
  public final InterviewerService interviewerService;
  public final ModelMapper mapper;

  @Autowired
  public InterviewerController(InterviewerService interviewerService, ModelMapper mapper) {
    this.interviewerService = interviewerService;
    this.mapper = mapper;
  }

  /**
   * Test generating of interviewer time slots. /interviewers/addSlots
   *
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
   * Test getting of interviewer time slots. /interviewers/getSlots
   *
   * @return list of interviewer slots in DB
   */
  @GetMapping("/getSlots")
  public List<InterviewerSlotDto> getSlots() {
    return interviewerService.findAll().stream()
        .map(e -> mapper.map(e, InterviewerSlotDto.class))
        .collect(Collectors.toList());
  }

  /**
   * Test deleting of interviewer time slots. /interviewers/delSlots
   *
   * @return list of interviewer slots in DB
   */
  @GetMapping("/delSlots")
  public List<InterviewerSlotDto> delSlots() {
    interviewerService.deleteAll();
    return interviewerService.findAll().stream()
        .map(e -> mapper.map(e, InterviewerSlotDto.class))
        .collect(Collectors.toList());
  }

  /**
   * Endpoint for adding a new interviewer time slot
   * using interviewer id from request.

   * @return response status
   */
  @PostMapping("/{interviewerId}/slots")
  public ResponseEntity<HttpStatus> addSlot(
      @RequestBody InterviewerSlotDto interviewerSlotDto,
      @PathVariable Long interviewerId) {
    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Endpoint for updating Interviewer slot
   * using id from request.

   * @return response status
   */
  @PostMapping("/{interviewerId}/slots/{slotId}")
  public ResponseEntity<HttpStatus> updateSlot(
      @RequestBody InterviewerSlotDto interviewerSlotDto,
      @PathVariable Long interviewerId, @PathVariable Long slotId) {
    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Endpoint for getting current week Interviewer slots.

   * @return response status
   */
  @GetMapping("/{interviewerId}/slots/current_week")
  public ResponseEntity<HttpStatus> getCurrentWeekSlots(@PathVariable Long interviewerId) {
    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Endpoint for getting next week Interviewer slots.

   * @return response status
   */
  @GetMapping("/{interviewerId}/slots/next_week")
  public ResponseEntity<HttpStatus> getNextWeekSlots(@PathVariable Long interviewerId) {
    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Endpoint for setting the maximum number of bookings for next week.

   * @return response status
   */
  @PostMapping("/{interviewerId}/bookings/next_week_count")
  public ResponseEntity<HttpStatus> setForNextWeekMaxBookings(
      @RequestBody Integer maxBookings,
      @PathVariable Long interviewerId) {
    return ResponseEntity.ok(HttpStatus.OK);
  }

}
