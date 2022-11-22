package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.controllers.dto.BookingDto;
import com.intellias.intellistart.interviewplanning.controllers.dto.CandidateSlotDto;
import com.intellias.intellistart.interviewplanning.controllers.dto.DashboardDayDto;
import com.intellias.intellistart.interviewplanning.controllers.dto.DashboardDayDto.CandidateSlotFormWithBookingIds;
import com.intellias.intellistart.interviewplanning.controllers.dto.DashboardDayDto.InterviewerSlotFormWithBookingIds;
import com.intellias.intellistart.interviewplanning.controllers.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.models.Booking;
import com.intellias.intellistart.interviewplanning.models.Candidate;
import com.intellias.intellistart.interviewplanning.models.Interviewer;
import com.intellias.intellistart.interviewplanning.models.User;
import com.intellias.intellistart.interviewplanning.models.enums.Role;
import com.intellias.intellistart.interviewplanning.repositories.UserRepository;
import com.intellias.intellistart.interviewplanning.util.exceptions.UserNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User service.
 */
@Service
@Transactional
public class UserService {

  private final UserRepository userRepository;
  private final InterviewerService interviewerService;
  private final CandidateService candidateService;
  private final BookingService bookingService;
  private final WeekService weekService;

  private final ModelMapper modelMapper;

  /**
   * Constructor for UserService.
   */
  @Autowired
  public UserService(UserRepository userRepository, InterviewerService interviewerService,
      CandidateService candidateService, BookingService bookingService, WeekService weekService,
      ModelMapper modelMapper) {
    this.userRepository = userRepository;
    this.interviewerService = interviewerService;
    this.candidateService = candidateService;
    this.bookingService = bookingService;
    this.weekService = weekService;
    this.modelMapper = modelMapper;
  }

  public void registerUser(User user) {
    userRepository.save(user);
  }

  /**
   * Method to register new User and add him to table
   * Interviewer/Coordinator if necessary.
   *
   * @param role used to set a Role and add User to right table if necessary
   * @return created User
   */
  public User registerUser(User user, Role role) {
    if (role == Role.CANDIDATE) {
      candidateService.registerCandidate(new Candidate(null, user));
    } else if (role == Role.INTERVIEWER) {
      interviewerService.registerInterviewer(new Interviewer(user, 0, null));
    }
    user.setRole(role);
    return userRepository.save(user);
  }

  public User findUserByEmail(String email) throws NullPointerException {
    return userRepository.findUserByEmail(email);
  }

  public List<User> findAllUsersByRole(Role role) {
    return userRepository.findAllByRole(role);
  }

  public void deleteUserById(Long id) {
    userRepository.deleteById(id);
  }

  public User findUserById(Long id) {
    return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
  }

  /**
   * Method for getting dashboard of input week.
   *
   * @param weekNum number of week to generate a dashboard
   * @return dashboard
   */
  public List<DashboardDayDto> getDashBoard(int weekNum) {
    List<DashboardDayDto> resultDashBoard = new ArrayList<>();
    for (int dayOfWeek = 1; dayOfWeek <= 7; dayOfWeek++) {
      DashboardDayDto dashboardDayDto = new DashboardDayDto();
      dashboardDayDto.setDay(WeekService.getDateFromWeekAndDay(weekNum, dayOfWeek));

      interviewerService.getSlotsForWeekAndDayOfWeek(weekNum, dayOfWeek)
          .forEach(slot -> dashboardDayDto.getInterviewerSlotFormsWithId()
              .add(new InterviewerSlotFormWithBookingIds(
                  modelMapper.map(slot, InterviewerSlotDto.class),
                  slot.getBooking().stream().map(Booking::getId).collect(
                      Collectors.toList()))));

      candidateService.getCandidateSlotsForWeekAndDayOfWeek(weekNum, dayOfWeek)
          .forEach(slot -> dashboardDayDto.getCandidateSlotsFormsWithId()
              .add(
                  new CandidateSlotFormWithBookingIds(modelMapper.map(slot, CandidateSlotDto.class),
                      slot.getBooking().stream().map(Booking::getId).collect(
                          Collectors.toList()))));

      int finalDayOfWeek = dayOfWeek;
      candidateService.getCandidateSlotsForWeek(weekNum)
          .forEach(slot -> slot.getBooking().stream().filter(
              booking -> weekService.getDayOfWeekFrom(booking.getFrom().toLocalDate())
                  == finalDayOfWeek).forEach(booking -> dashboardDayDto.getBookings()
              .put(booking.getId(), modelMapper.map(booking, BookingDto.class))));

      interviewerService.getSlotsForWeek(weekNum)
          .forEach(slot -> slot.getBooking().stream().filter(
              booking -> weekService.getDayOfWeekFrom(booking.getFrom().toLocalDate())
                  == finalDayOfWeek).forEach(booking -> dashboardDayDto.getBookings()
              .put(booking.getId(), modelMapper.map(booking, BookingDto.class))));
      resultDashBoard.add(dashboardDayDto);
    }
    return resultDashBoard;
  }
}
