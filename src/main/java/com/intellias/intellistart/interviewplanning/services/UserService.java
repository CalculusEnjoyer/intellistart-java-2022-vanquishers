package com.intellias.intellistart.interviewplanning.services;

import com.intellias.intellistart.interviewplanning.controllers.dto.BookingDto;
import com.intellias.intellistart.interviewplanning.controllers.dto.CandidateSlotDto;
import com.intellias.intellistart.interviewplanning.controllers.dto.DayForm;
import com.intellias.intellistart.interviewplanning.controllers.dto.DayForm.CandidateSlotFormWithId;
import com.intellias.intellistart.interviewplanning.controllers.dto.DayForm.InterviewerSlotFormWithId;
import com.intellias.intellistart.interviewplanning.controllers.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.models.Booking;
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

  public void register(User user) {
    userRepository.save(user);
  }

  public User findUserByFacebookId(Long facebookId) {
    return userRepository.findByFacebookId(facebookId);
  }

  public User findUserByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
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

  public List<DayForm> getDashBoard(int weekNum) {
    List<DayForm> resultDashBoard = new ArrayList<>();
    for (int i = 1; i <= 7; i++) {
      DayForm dayForm = new DayForm();
      dayForm.setDay(i);

      interviewerService.getSlotsForWeek(weekNum).stream()
          .filter(slot -> slot.getDayOfWeek() == dayForm.getDay())
          .forEach(slot -> dayForm.getInterviewerSlotFormsWithId()
              .add(new InterviewerSlotFormWithId(modelMapper.map(slot, InterviewerSlotDto.class),
                  slot.getBooking().stream().map(Booking::getId).collect(
                      Collectors.toList()))));

      candidateService.getCandidateSlotsForWeek(weekNum).stream().filter(
              slot -> weekService.getDayOfWeek(
                  slot.getDateFrom().toLocalDate()) == dayForm.getDay())
          .forEach(slot -> dayForm.getCandidateSlotsFormsWithId()
              .add(new CandidateSlotFormWithId(modelMapper.map(slot, CandidateSlotDto.class),
                  slot.getBooking().stream().map(Booking::getId).collect(
                      Collectors.toList()))));

      candidateService.getCandidateSlotsForWeek(weekNum)
          .forEach(slot -> slot.getBooking().stream().filter(
              booking -> weekService.getDayOfWeek(booking.getFrom().toLocalDate())
                  == dayForm.getDay()).forEach(booking -> dayForm.getBookings()
              .put(booking.getId(), modelMapper.map(booking, BookingDto.class))));

      interviewerService.getSlotsForWeek(weekNum)
          .forEach(slot -> slot.getBooking().stream().filter(
              booking -> weekService.getDayOfWeek(booking.getFrom().toLocalDate())
                  == dayForm.getDay()).forEach(booking -> dayForm.getBookings()
              .put(booking.getId(), modelMapper.map(booking, BookingDto.class))));
      resultDashBoard.add(dayForm);
    }
    return resultDashBoard;
  }
}
