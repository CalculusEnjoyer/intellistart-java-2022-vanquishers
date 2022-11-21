package com.intellias.intellistart.interviewplanning.controllers;


import static java.util.Calendar.YEAR;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.intellias.intellistart.interviewplanning.models.Booking;
import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import com.intellias.intellistart.interviewplanning.models.Interviewer;
import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.models.User;
import com.intellias.intellistart.interviewplanning.models.enums.Role;
import com.intellias.intellistart.interviewplanning.models.enums.Status;
import com.intellias.intellistart.interviewplanning.repositories.BookingRepository;
import com.intellias.intellistart.interviewplanning.repositories.CandidateSlotRepository;
import com.intellias.intellistart.interviewplanning.repositories.InterviewerSlotRepository;
import com.intellias.intellistart.interviewplanning.services.BookingService;
import com.intellias.intellistart.interviewplanning.services.CandidateService;
import com.intellias.intellistart.interviewplanning.services.InterviewerService;
import com.intellias.intellistart.interviewplanning.services.UserService;
import com.intellias.intellistart.interviewplanning.util.exceptions.BookingNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class CoordinatorControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private InterviewerService interviewerService;

  @Autowired
  private ModelMapper mapper;

  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private InterviewerSlotRepository interviewerSlotRepository;
  @Autowired
  private CandidateSlotRepository candidateSlotRepository;

  @Autowired
  private BookingService bookingService;

  @Autowired
  private UserService userService;

  @Autowired
  private CandidateService candidateService;

  @Autowired
  private WebApplicationContext webApplicationContext;

  private static final List<User> USERS = List.of(
      new User("email1@gmail.com", Role.INTERVIEWER),
      new User("email2@gmail.com", Role.INTERVIEWER),
      new User("email3@gmail.com", Role.COORDINATOR),
      new User("email4@gmail.com", Role.COORDINATOR));

  private static final List<Interviewer> INTERVIEWERS = List.of(
      new Interviewer(USERS.get(0), 5, null),
      new Interviewer(USERS.get(1), 2, null));


  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    USERS.forEach(u -> userService.register(u));
    INTERVIEWERS.forEach(i -> interviewerService.registerInterviewer(i));
  }

  @Test
  @Order(1)
  void deleteBookingTest() throws Exception {
    Booking booking = new Booking(LocalDateTime.of(2015,
        Month.JULY, 29, 19, 30), LocalDateTime.of(2015,
        Month.JULY, 29, 21, 30), "check", "check", Status.BOOKED);
    InterviewerSlot intslot = interviewerSlotRepository.save(new InterviewerSlot(0, 1,
        LocalTime.of(9, 30), LocalTime.of(11, 0)));
    CandidateSlot candslot = candidateSlotRepository.save(new CandidateSlot(
        LocalDateTime.of(LocalDate.of(YEAR, Month.DECEMBER, 12), LocalTime.of(9, 30)),
        LocalDateTime.of(LocalDate.of(YEAR, Month.DECEMBER, 12), LocalTime.of(18, 0))));
    booking.setInterviewerSlot(intslot);
    booking.setCandidateSlot(candslot);
    bookingRepository.save(booking);
    booking = bookingService.getAllBookings().get(bookingService.getAllBookings().size() - 1);

    mockMvc.perform(delete("/bookings/{bookingId}", booking.getId()))
        .andExpect(status().isOk());

    try {
      bookingService.getBookingById(booking.getId());
      fail();
    } catch (BookingNotFoundException e) {
      assertNotNull(e);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  @Order(2)
  void revokeCoordinatorRoleByIdTest() throws Exception {
    User coordinator = new User("check@gmail.com", Role.COORDINATOR);
    userService.register(coordinator);

    mockMvc.perform(delete("/users/coordinators/{coordinatorId}", coordinator.getId()))
        .andExpect(status().isOk());

    Assertions.assertEquals(Role.CANDIDATE,
        userService.findUserById(coordinator.getId()).getRole());
  }

  @Test
  @Order(3)
  void getCoordinatorsTest() throws Exception {
    List<User> coordinators = USERS.stream().filter(u -> u.getRole() == Role.COORDINATOR)
        .collect(Collectors.toList());

    mockMvc.perform(
            MockMvcRequestBuilders.get("/users/coordinators").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(userService.findAllUsersByRole(Role.COORDINATOR).size())))
        .andExpect(jsonPath("$[0].role", equalTo("COORDINATOR")))
        .andExpect(jsonPath("$[1].role", equalTo("COORDINATOR")))
        .andExpect(jsonPath("$[0].email", equalTo(coordinators.get(0).getEmail())));
  }

  @Test
  @Order(4)
  void grantCoordinatorRoleTest() throws Exception {
    userService.register(new User("example34@gmail.com", Role.INTERVIEWER));

    mockMvc.perform(post("/users/coordinators")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\":\"example34@gmail.com\"}")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    Assertions.assertEquals(Role.COORDINATOR,
        userService.findUserByEmail("example34@gmail.com").getRole());
  }

  @Test
  @Order(5)
  void getAllInterviewersTest() throws Exception {
    mockMvc.perform(
            MockMvcRequestBuilders.get("/users/interviewers").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(interviewerService.getAllInterviewers().size())))
        .andExpect(jsonPath("$[0].bookingLimit", equalTo(INTERVIEWERS.get(0).getBookingLimit())))
        .andExpect(jsonPath("$[1].user.email", equalTo(INTERVIEWERS.get(1).getUser().getEmail())));
  }

  @Test
  @Order(6)
  void revokeInterviewerRoleTest() throws Exception {
    User interviewerUser = new User("check@gmail.com", Role.INTERVIEWER);
    userService.register(interviewerUser);
    Interviewer interviewer = new Interviewer(interviewerUser, 3, null);
    interviewerService.registerInterviewer(interviewer);

    mockMvc.perform(delete("/users/interviewers/{interviewerId}", interviewer.getId()))
        .andExpect(status().isOk());

    Assertions.assertEquals(Role.CANDIDATE,
        userService.findUserById(interviewerUser.getId()).getRole());
  }
}
