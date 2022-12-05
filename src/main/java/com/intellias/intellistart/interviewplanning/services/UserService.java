package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.controllers.dto.BookingDto;
import com.intellias.intellistart.interviewplanning.controllers.dto.CandidateSlotDto;
import com.intellias.intellistart.interviewplanning.controllers.dto.DashboardDayDto;
import com.intellias.intellistart.interviewplanning.controllers.dto.DashboardDayDto.CandidateSlotFormWithBookingIds;
import com.intellias.intellistart.interviewplanning.controllers.dto.DashboardDayDto.InterviewerSlotFormWithBookingIds;
import com.intellias.intellistart.interviewplanning.controllers.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.models.Booking;
import com.intellias.intellistart.interviewplanning.models.Candidate;
import com.intellias.intellistart.interviewplanning.models.User;
import com.intellias.intellistart.interviewplanning.models.enums.Role;
import com.intellias.intellistart.interviewplanning.repositories.CandidateRepository;
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
  private final CandidateRepository candidateRepository;
  private final InterviewerService interviewerService;
  private final CandidateService candidateService;
  private final ModelMapper modelMapper;
  private final WeekService weekService;

  /**
   * Constructor for UserService.
   */
  @Autowired
  public UserService(UserRepository userRepository, CandidateRepository candidateRepository,
      InterviewerService interviewerService,
      CandidateService candidateService, ModelMapper modelMapper, WeekService weekService) {
    this.userRepository = userRepository;
    this.candidateRepository = candidateRepository;
    this.interviewerService = interviewerService;
    this.candidateService = candidateService;
    this.modelMapper = modelMapper;
    this.weekService = weekService;
  }

  public void registerUser(User user) {
    userRepository.save(user);
  }

  public User registerCandidate(User user) {
    candidateRepository.save(new Candidate(null, user));
    return userRepository.save(user);
  }

  public User findUserByEmail(String email) throws NullPointerException {
    return userRepository.findUserByEmail(email);
  }

  public List<User> findAllUsersByRole(Role role) {
    return userRepository.findAllByRole(role);
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
      dashboardDayDto.setDay(weekService.getDateFromWeekAndDay(weekNum, dayOfWeek));

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
