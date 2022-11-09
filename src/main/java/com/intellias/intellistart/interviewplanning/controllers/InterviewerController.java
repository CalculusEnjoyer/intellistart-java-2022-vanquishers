package com.intellias.intellistart.interviewplanning.controllers;

import com.intellias.intellistart.interviewplanning.controllers.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.models.Interviewer;
import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.services.InterviewerService;
import com.intellias.intellistart.interviewplanning.services.WeekService;
import com.intellias.intellistart.interviewplanning.util.validation.InterviewerValidator;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
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
@RequestMapping("/interviewers")
public class InterviewerController {

  private final InterviewerService interviewerService;
  private final WeekService weekService;
  public final ModelMapper mapper;

  /**
   * Constructor for CoordinatorController.
   */
  @Autowired
  public InterviewerController(InterviewerService interviewerService,
      ModelMapper mapper, WeekService weekService) {
    this.interviewerService = interviewerService;
    this.weekService = weekService;
    this.mapper = mapper;
  }

  /**
   * Endpoint for adding a new interviewer time slot using interviewer id from request.
   *
   * @return added slot as DTO
   */
  @PostMapping("/{interviewerId}/slots")
  public ResponseEntity<InterviewerSlotDto> addSlot(
      @Valid @RequestBody InterviewerSlotDto slotDto,
      @PathVariable Long interviewerId) {
    InterviewerSlot slot = mapper.map(slotDto, InterviewerSlot.class);
    Interviewer interviewer = interviewerService.getInterviewerById(interviewerId);
    slot.setInterviewer(interviewer);

    InterviewerValidator.validateSlotWeekNum(slotDto.getWeekNum(), weekService.getNextWeekNum());
    InterviewerValidator.validateSlot(slot);

    InterviewerSlot registeredSlot = interviewerService.registerSlot(slot);
    InterviewerSlotDto registeredSlotDto = mapper.map(registeredSlot, InterviewerSlotDto.class);
    return ResponseEntity.ok(registeredSlotDto);
  }

  /**
   * Endpoint for updating Interviewer slot using id from request.
   *
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
   *
   * @return response status
   */
  @GetMapping("/{interviewerId}/slots/current_week")
  public ResponseEntity<List<InterviewerSlotDto>> getCurrentWeekSlots(
      @PathVariable Long interviewerId) {
    List<InterviewerSlotDto> currentWeekSlots = interviewerService
        .getSlotsForIdAndWeek(interviewerId, weekService.getCurrentWeekNum())
        .stream()
        .map(slot -> mapper.map(slot, InterviewerSlotDto.class))
        .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK)
        .body(currentWeekSlots);
  }

  /**
   * Endpoint for getting next week Interviewer slots.
   *
   * @return response status
   */
  @GetMapping("/{interviewerId}/slots/next_week")
  public ResponseEntity<List<InterviewerSlotDto>> getNextWeekSlots(
      @PathVariable Long interviewerId) {
    List<InterviewerSlotDto> nextWeekSlots = interviewerService
        .getSlotsForIdAndWeek(interviewerId, weekService.getNextWeekNum())
        .stream()
        .map(slot -> mapper.map(slot, InterviewerSlotDto.class))
        .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK)
        .body(nextWeekSlots);
  }

  /**
   * Endpoint for setting the maximum number of bookings for next week.
   *
   * @return response status
   */
  @PostMapping("/{interviewerId}/bookings/next_week_count")
  public ResponseEntity<Integer> setForNextWeekMaxBookings(
      @RequestBody Integer bookingLimit,
      @PathVariable Long interviewerId) {
    InterviewerValidator.validateBookingLimit(bookingLimit);

    Interviewer interviewer = interviewerService.getInterviewerById(interviewerId);
    interviewer.setBookingLimit(bookingLimit);
    Interviewer registeredInterviewer = interviewerService.registerInterviewer(interviewer);
    return ResponseEntity.ok(registeredInterviewer.getBookingLimit());
  }
}
