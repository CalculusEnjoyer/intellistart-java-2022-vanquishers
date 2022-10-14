package com.intellias.intellistart.interviewplanning.controllers;

import com.intellias.intellistart.interviewplanning.controllers.dto.BookingDto;
import com.intellias.intellistart.interviewplanning.controllers.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.models.Interviewer;
import com.intellias.intellistart.interviewplanning.models.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Coordinator controller.
 */
@RestController
@RequestMapping(CoordinatorController.MAPPING)
public class CoordinatorController {

  public static final String MAPPING = "/";

  public final ModelMapper mapper;

  @Autowired
  public CoordinatorController(ModelMapper mapper) {
    this.mapper = mapper;
  }

  /**
   * Method for updating exact time slot by interviewer.
   *
   * @return response status
   */
  @PostMapping("/interviewers/{interviewerId}/slots/{slotId}")
  public ResponseEntity<HttpStatus> updateInterviewerSlot(
      @RequestBody InterviewerSlotDto interviewerSlotDto,
      @PathVariable Long interviewerId, @PathVariable Long slotId) {

    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Method for getting all the candidates and interviewers slots grouped by days, each day:
   * contains all interviewers slots with booking's IDs inside contains all candidates slots with
   * booking's IDs inside contains map of bookings as map bookingId => bookingData.
   *
   * @return list
   */
  @GetMapping("/weeks/{weekId}/dashboard")
  public List<String> getAllSlots(@PathVariable Long weekId) {

    return new ArrayList<>();
  }

  /**
   * Method for creating booking.
   *
   * @return response status
   */
  @PostMapping("/bookings")
  public ResponseEntity<HttpStatus> createBooking(@RequestBody Long interviewerSlotId,
      @RequestBody Long candidateSlotId, @RequestBody LocalDateTime startTime,
      @RequestBody LocalDateTime endTime, @RequestBody String subject,
      @RequestBody String description) {

    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Method for updating booking.
   *
   * @return response status
   */
  @PostMapping("/bookings/{bookingId}")
  public ResponseEntity<HttpStatus> updateBooking(@PathVariable Long bookingId,
      @RequestBody BookingDto bookingDto) {

    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Method for deleting booking.
   *
   * @return response status
   */
  @DeleteMapping("/bookings/{bookingId}")
  public ResponseEntity<HttpStatus> deleteBooking(@PathVariable Long bookingId) {

    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Method for granting the Interviewer role.
   *
   * @return response status
   */
  @PostMapping("/users/interviewers")
  public ResponseEntity<HttpStatus> grantInterviewerRole(@RequestBody String email) {

    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Method for getting all interviewers.
   *
   * @return response status
   */
  @GetMapping("/users/interviewers")
  public List<Interviewer> getInterviewers() {

    return new ArrayList<>();
  }

  /**
   * Method for revoking the Interviewer role.
   *
   * @return response status
   */
  @DeleteMapping("/users/interviewers/{interviewerId}")
  public ResponseEntity<HttpStatus> revokeInterviewerRole(@PathVariable Long interviewerId) {

    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Method for granting the Coordinator role.
   *
   * @return response status
   */
  @PostMapping("/users/coordinators")
  public ResponseEntity<HttpStatus> grantCoordinatorRole(@RequestBody String email) {

    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Method for getting all coordinators.
   *
   * @return response status
   */
  @GetMapping("/users/coordinators")
  public List<User> getCoordinators() {

    return new ArrayList<>();
  }

  /**
   * Method for revoking the Coordinator role.
   *
   * @return response status
   */
  @DeleteMapping("/users/coordinators/{coordinatorId}")
  public ResponseEntity<HttpStatus> revokeCoordinatorRole(@PathVariable Long coordinatorId) {

    return ResponseEntity.ok(HttpStatus.OK);
  }
}