package com.intellias.intellistart.interviewplanning.controllers;

import com.intellias.intellistart.interviewplanning.controllers.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.models.Interviewer;
import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.models.User;
import com.intellias.intellistart.interviewplanning.models.enums.Role;
import com.intellias.intellistart.interviewplanning.models.security.FacebookUserDetails;
import com.intellias.intellistart.interviewplanning.services.InterviewerService;
import com.intellias.intellistart.interviewplanning.services.WeekService;
import com.intellias.intellistart.interviewplanning.util.validation.InterviewerValidator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
  private final ModelMapper mapper;
  private final InterviewerValidator interviewerValidator;

  /**
   * Constructor for CoordinatorController.
   */
  @Autowired
  public InterviewerController(InterviewerService interviewerService,
      ModelMapper mapper, WeekService weekService, InterviewerValidator interviewerValidator) {
    this.interviewerService = interviewerService;
    this.weekService = weekService;
    this.mapper = mapper;
    this.interviewerValidator = interviewerValidator;
  }

  /**
   * Endpoint for adding a new interviewer time slot using the interviewer userId in {interviewerId}
   * from request. Slot can be created only for next week (until Friday 00:00), or for weeks after
   * the next week.
   *
   * @return added slot as DTO
   */
  @PostMapping("/{interviewerId}/slots")
  @RolesAllowed("ROLE_INTERVIEWER")
  public ResponseEntity<InterviewerSlotDto> addSlot(
      @Valid @RequestBody InterviewerSlotDto slotDto,
      @PathVariable Long interviewerId,
      Authentication authentication) {
    Long authUserId = ((FacebookUserDetails) authentication.getPrincipal()).getUser().getId();
    interviewerValidator.validateUserIdMatch(authUserId, interviewerId);

    if (slotDto.getWeekNum() == null) {
      slotDto.setWeekNum(weekService.getNextWeekNum());
    }
    interviewerValidator.validateSlotCreateForDto(slotDto);

    InterviewerSlot slot = mapper.map(slotDto, InterviewerSlot.class);
    slot.setInterviewer(interviewerService.getInterviewerByUserId(authUserId));

    InterviewerSlot registeredSlot = interviewerService.registerSlot(slot);
    return ResponseEntity.ok(mapper.map(registeredSlot, InterviewerSlotDto.class));
  }

  /**
   * Endpoint for updating Interviewer slot using interviewer's user id from request. Slot can be
   * updated by the Coordinator (any slot registered at current week or next weeks), or by the
   * Interviewer (only their own slots for next week until Friday 00:00 of current week, or for
   * weeks after the next week, at any time).
   *
   * @return response status
   */
  @PostMapping("/{interviewerId}/slots/{slotId}")
  @RolesAllowed({"ROLE_INTERVIEWER", "ROLE_COORDINATOR"})
  public ResponseEntity<InterviewerSlotDto> updateSlot(
      @Valid @RequestBody InterviewerSlotDto slotDto,
      @PathVariable Long interviewerId, @PathVariable Long slotId,
      Authentication authentication) {
    User authUser = ((FacebookUserDetails) authentication.getPrincipal()).getUser();

    if (authUser.getRole().equals(Role.INTERVIEWER)) {
      interviewerValidator.validateUserIdMatch(authUser.getId(), interviewerId);
    }

    interviewerValidator.validateSlotUpdateForDtoAndRole(slotDto, authUser.getRole());
    //voiding the interviewer id in dto, so it will not be mapped in the slot
    slotDto.setInterviewerId(null);

    InterviewerSlot slot = interviewerService.getSlotById(slotId);

    Long pathInterviewerId = interviewerService.getInterviewerByUserId(interviewerId).getId();
    interviewerValidator.validateHasAccessToSlot(pathInterviewerId, slot);
    interviewerValidator.validateSlotUpdateForWeekNumAndRole(
        slot.getWeekNum(), authUser.getRole());
    mapper.map(slotDto, slot);

    InterviewerSlot updatedSlot = interviewerService.registerSlot(slot);
    return ResponseEntity.ok(mapper.map(updatedSlot, InterviewerSlotDto.class));
  }

  /**
   * Endpoint for getting current week Interviewer slots for Interviewer with userId provided in
   * {interviewerId} in the path.
   *
   * @return response status
   */
  @GetMapping("/{interviewerId}/slots/current-week")
  @RolesAllowed("ROLE_INTERVIEWER")
  public ResponseEntity<List<InterviewerSlotDto>> getCurrentWeekSlots(
      @PathVariable Long interviewerId,
      Authentication authentication) {
    Long authUserId = ((FacebookUserDetails) authentication.getPrincipal()).getUser().getId();
    interviewerValidator.validateUserIdMatch(authUserId, interviewerId);

    List<InterviewerSlotDto> currentWeekSlots = interviewerService
        .getSlotsForIdAndWeek(interviewerId, weekService.getCurrentWeekNum())
        .stream()
        .map(slot -> mapper.map(slot, InterviewerSlotDto.class))
        .collect(Collectors.toList());

    return ResponseEntity.ok(currentWeekSlots);
  }

  /**
   * Endpoint for getting next week Interviewer slots for Interviewer with userId provided in
   * {interviewerId} in the path.
   *
   * @return response status
   */
  @GetMapping("/{interviewerId}/slots/next-week")
  @RolesAllowed("ROLE_INTERVIEWER")
  public ResponseEntity<List<InterviewerSlotDto>> getNextWeekSlots(
      @PathVariable Long interviewerId,
      Authentication authentication) {
    Long authUserId = ((FacebookUserDetails) authentication.getPrincipal()).getUser().getId();
    interviewerValidator.validateUserIdMatch(authUserId, interviewerId);

    List<InterviewerSlotDto> nextWeekSlots = interviewerService
        .getSlotsForIdAndWeek(interviewerId, weekService.getNextWeekNum())
        .stream()
        .map(slot -> mapper.map(slot, InterviewerSlotDto.class))
        .collect(Collectors.toList());

    return ResponseEntity.ok(nextWeekSlots);
  }

  /**
   * Endpoint for setting the maximum number of bookings for next week for the Interviewer with
   * userId provided in {interviewerId} in the path. .
   *
   * @return response status
   */
  @PostMapping("/{interviewerId}/bookings/booking-limit")
  @RolesAllowed("ROLE_INTERVIEWER")
  public ResponseEntity<Map<String, Integer>> setBookingLimit(
      @RequestBody Map<String, Integer> bookingLimitMap,
      @PathVariable Long interviewerId,
      Authentication authentication) {
    Long authUserId = ((FacebookUserDetails) authentication.getPrincipal()).getUser().getId();
    interviewerValidator.validateUserIdMatch(authUserId, interviewerId);

    Integer bookingLimit = bookingLimitMap.get("bookingLimit");
    interviewerValidator.validateBookingLimit(bookingLimit);

    Interviewer interviewer = interviewerService.getInterviewerByUserId(interviewerId);
    interviewer.setBookingLimit(bookingLimit);
    Interviewer registeredInterviewer = interviewerService.registerInterviewer(interviewer);
    return ResponseEntity.ok(Map.of("bookingLimit", registeredInterviewer.getBookingLimit()));
  }

}
