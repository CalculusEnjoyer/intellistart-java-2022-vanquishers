package com.intellias.intellistart.interviewplanning.controllers;

import com.intellias.intellistart.interviewplanning.controllers.dto.BookingDto;
import com.intellias.intellistart.interviewplanning.controllers.dto.BookingForm;
import com.intellias.intellistart.interviewplanning.controllers.dto.DashboardDayDto;
import com.intellias.intellistart.interviewplanning.controllers.dto.InterviewerDto;
import com.intellias.intellistart.interviewplanning.controllers.dto.UserDto;
import com.intellias.intellistart.interviewplanning.models.Booking;
import com.intellias.intellistart.interviewplanning.models.Candidate;
import com.intellias.intellistart.interviewplanning.models.Interviewer;
import com.intellias.intellistart.interviewplanning.models.User;
import com.intellias.intellistart.interviewplanning.models.enums.Role;
import com.intellias.intellistart.interviewplanning.models.enums.Status;
import com.intellias.intellistart.interviewplanning.services.BookingService;
import com.intellias.intellistart.interviewplanning.services.CandidateService;
import com.intellias.intellistart.interviewplanning.services.InterviewerService;
import com.intellias.intellistart.interviewplanning.services.UserService;
import com.intellias.intellistart.interviewplanning.services.WeekService;
import com.intellias.intellistart.interviewplanning.util.exceptions.ExcessBookingLimitException;
import com.intellias.intellistart.interviewplanning.util.exceptions.SameRoleChangeException;
import com.intellias.intellistart.interviewplanning.util.exceptions.UserNotFoundException;
import com.intellias.intellistart.interviewplanning.util.validation.BookingValidator;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
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
@RolesAllowed("ROLE_COORDINATOR")
@RestController
@RequestMapping(CoordinatorController.MAPPING)
public class CoordinatorController {

  public static final String MAPPING = "/";

  public final ModelMapper mapper;

  public final BookingService bookingService;

  public final InterviewerService interviewerService;

  public final CandidateService candidateService;

  public final UserService userService;

  public final WeekService weekService;
  public final BookingValidator bookingValidator;


  /**
   * Constructor for CoordinatorController.
   */
  @Autowired
  public CoordinatorController(ModelMapper mapper, BookingService bookingService,
      InterviewerService interviewerService, CandidateService candidateService,
      UserService userService, WeekService weekService, BookingValidator bookingValidator) {
    this.mapper = mapper;
    this.bookingService = bookingService;
    this.interviewerService = interviewerService;
    this.candidateService = candidateService;
    this.userService = userService;
    this.weekService = weekService;
    this.bookingValidator = bookingValidator;
  }

  /**
   * Method for getting all the candidates and interviewers slots grouped by days, each day:
   * contains all interviewers slots with bookings IDs inside contains all candidates slots with
   * bookings IDs inside contains map of bookings as map bookingId => bookingData.
   *
   * @return list
   */
  @GetMapping("/weeks/{weekId}/dashboard")
  public List<DashboardDayDto> getAllSlots(@PathVariable int weekId) {
    return userService.getDashBoard(weekId);
  }

  /**
   * Method for creating bookings.
   *
   * @return response status
   */
  @PostMapping("/bookings")
  public ResponseEntity<BookingForm> createBooking(@RequestBody BookingDto bookingDto) {
    bookingValidator.validDtoBoundariesOrError(bookingDto);
    Booking booking = mapper.map(bookingDto, Booking.class);
    Interviewer interviewer = interviewerService.getSlotById(
        bookingDto.getInterviewerSlotId()).getInterviewer();
    int bookingLimit = interviewer.getBookingLimit();
    List<Booking> bookingsByInterviewerId = bookingService
        .findByInterviewerIdAndWeekNum(interviewer.getId(), weekService.getCurrentWeekNum());

    if (bookingsByInterviewerId.size() >= bookingLimit) {
      throw new ExcessBookingLimitException();
    }

    booking.setStatus(Status.NEW);
    bookingDto.setStatus(Status.NEW);

    bookingService.registerBooking(booking);
    return ResponseEntity.status(HttpStatus.OK).body(mapper.map(booking, BookingForm.class));
  }

  /**
   * Method for updating bookings.
   *
   * @return response status
   */
  @PostMapping("/bookings/{bookingId}")
  public ResponseEntity<BookingForm> updateBooking(@PathVariable Long bookingId,
      @RequestBody BookingDto bookingDto) {
    bookingValidator.validDtoBoundariesOrError(bookingDto);
    Booking bookingToUpdate = bookingService.getBookingById(bookingId);
    Booking.updateFieldsExceptId(bookingToUpdate, mapper.map(bookingDto, Booking.class));
    bookingToUpdate.setStatus(Status.CHANGED);
    bookingService.registerBooking(bookingToUpdate);

    return ResponseEntity.ok().body(mapper.map(bookingToUpdate, BookingForm.class));
  }

  /**
   * Method for deleting bookings.
   *
   * @return response status
   */
  @DeleteMapping("/bookings/{bookingId}")
  public ResponseEntity<BookingForm> deleteBooking(@PathVariable Long bookingId) {
    Booking deletedBooking = bookingService.getBookingById(bookingId);
    bookingService.deleteBookingById(bookingId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(mapper.map(deletedBooking, BookingForm.class));
  }

  /**
   * Method for granting the Interviewer role.
   *
   * @return response status
   */
  @PostMapping("/users/interviewers")
  public ResponseEntity<UserDto> grantInterviewerRole(@RequestBody Map<String, String> email) {
    User userToGrand = userService.findUserByEmail(email.get("email"));
    if (userToGrand.getRole() == Role.INTERVIEWER) {
      throw new SameRoleChangeException();
    }
    userToGrand.setRole(Role.INTERVIEWER);
    userService.registerUser(userToGrand);
    Interviewer interviewer;
    try {
      interviewer = interviewerService.getInterviewerByUserId(userToGrand.getId());
    } catch (UserNotFoundException e) {
      interviewer = new Interviewer();
    }
    interviewer.setUser(userToGrand);
    interviewerService.registerInterviewer(interviewer);
    return ResponseEntity.ok().body(mapper.map(userToGrand, UserDto.class));
  }

  /**
   * Method for getting all interviewers.
   *
   * @return response status
   */
  @GetMapping("/users/interviewers")
  public ResponseEntity<List<InterviewerDto>> getInterviewers() {
    List<InterviewerDto> interviewerDtos = interviewerService
        .getAllInterviewers()
        .stream()
        .filter(i -> i.getUser().getRole() == Role.INTERVIEWER)
        .map(interviewer -> mapper.map(interviewer, InterviewerDto.class))
        .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(interviewerDtos);
  }

  /**
   * Method for revoking the Interviewer role.
   *
   * @return response status
   */
  @DeleteMapping("/users/interviewers/{interviewerId}")
  public ResponseEntity<InterviewerDto> revokeInterviewerRole(@PathVariable Long interviewerId) {
    User userToDowngrade = userService.findUserById(interviewerId);
    userToDowngrade.setRole(Role.CANDIDATE);
    userService.registerUser(userToDowngrade);
    Interviewer interviewerToDelete = interviewerService.getInterviewerByUserId(
        userToDowngrade.getId());
    Interviewer interviewer = interviewerService.getInterviewerByUserId(userToDowngrade.getId());
    if (interviewer.getInterviewerSlot().isEmpty()) {
      interviewerService.deleteInterviewerById(interviewer.getId());
    }
    return ResponseEntity.ok().body(mapper.map(interviewerToDelete, InterviewerDto.class));
  }

  /**
   * Method for granting the Coordinator role.
   *
   * @return response status
   */
  @PostMapping("/users/coordinators")
  public ResponseEntity<UserDto> grantCoordinatorRole(@RequestBody Map<String, String> email) {
    User userToGrand = userService.findUserByEmail(email.get("email"));
    if (userToGrand.getRole() == Role.COORDINATOR) {
      throw new SameRoleChangeException();
    }
    userToGrand.setRole(Role.COORDINATOR);
    userService.registerUser(userToGrand);
    return ResponseEntity.ok().body(mapper.map(userToGrand, UserDto.class));
  }

  /**
   * Method for getting all coordinators.
   *
   * @return response status
   */
  @GetMapping("/users/coordinators")
  public List<UserDto> getCoordinators() {
    return userService.findAllUsersByRole(Role.COORDINATOR).stream().sorted(
        Comparator.comparing(User::getId)).map(user -> mapper.map(user, UserDto.class)).collect(
        Collectors.toList());
  }

  /**
   * Method for revoking the Coordinator role.
   *
   * @return response status
   */
  @DeleteMapping("/users/coordinators/{coordinatorId}")
  public ResponseEntity<UserDto> revokeCoordinatorRole(@PathVariable Long coordinatorId) {
    User user = userService.findUserById(coordinatorId);
    if (user.getRole() != Role.COORDINATOR) {
      throw new UserNotFoundException();
    }
    user.setRole(Role.CANDIDATE);
    userService.registerUser(user);
    Candidate candidate;
    try {
      candidate = candidateService.getCandidateByUserId(user.getId());
    } catch (UserNotFoundException e) {
      candidate = new Candidate();
    }
    candidate.setUser(user);
    candidateService.registerCandidate(candidate);
    return ResponseEntity.ok().body(mapper.map(user, UserDto.class));
  }
}
