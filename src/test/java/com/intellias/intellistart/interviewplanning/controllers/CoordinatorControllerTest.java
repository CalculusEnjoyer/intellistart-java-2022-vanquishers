package com.intellias.intellistart.interviewplanning.controllers;


import static java.util.Calendar.YEAR;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.intellias.intellistart.interviewplanning.models.Booking;
import com.intellias.intellistart.interviewplanning.models.Candidate;
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
import com.intellias.intellistart.interviewplanning.services.WeekService;
import com.intellias.intellistart.interviewplanning.util.exceptions.BookingNotFoundException;
import com.intellias.intellistart.interviewplanning.util.exceptions.SameRoleChangeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
@WithMockUser(roles = {"COORDINATOR"})
class CoordinatorControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private InterviewerService interviewerService;

  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private InterviewerSlotRepository interviewerSlotRepository;

  @Autowired
  private CandidateSlotRepository candidateSlotRepository;

  @Autowired
  private BookingService bookingService;

  @Autowired
  private WeekService weekService;

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
      new User("email4@gmail.com", Role.COORDINATOR),
      new User("email5@gmail.com", Role.CANDIDATE),
      new User("email6@gmail.com", Role.CANDIDATE));

  private static final List<Interviewer> INTERVIEWERS = List.of(
      new Interviewer(USERS.get(0), 5, new HashSet<>()),
      new Interviewer(USERS.get(1), 2, new HashSet<>()));

  private static final List<Candidate> CANDIDATES = List.of(
      new Candidate(new HashSet<>(), USERS.get(4)),
      new Candidate(new HashSet<>(), USERS.get(5)));


  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    USERS.forEach(u -> userService.registerUser(u));
    INTERVIEWERS.forEach(i -> interviewerService.registerInterviewer(i));
    CANDIDATES.forEach(i -> candidateService.registerCandidate(i));
  }

  @Test
  @Order(1)
  void deleteBookingTest() throws Exception {
    Booking booking = new Booking(LocalDateTime.of(2015,
        Month.JULY, 29, 19, 30), LocalDateTime.of(2015,
        Month.JULY, 29, 21, 30), "check", "check", Status.BOOKED);
    InterviewerSlot intSlot = interviewerSlotRepository.save(new InterviewerSlot(0, 1,
        LocalTime.of(9, 30), LocalTime.of(11, 0)));
    CandidateSlot candSlot = candidateSlotRepository.save(new CandidateSlot(
        LocalDateTime.of(LocalDate.of(YEAR, Month.DECEMBER, 12), LocalTime.of(9, 30)),
        LocalDateTime.of(LocalDate.of(YEAR, Month.DECEMBER, 12), LocalTime.of(18, 0))));
    booking.setInterviewerSlot(intSlot);
    booking.setCandidateSlot(candSlot);
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
    userService.registerUser(coordinator);

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
    userService.registerUser(new User("example34@gmail.com", Role.INTERVIEWER));

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
    userService.registerUser(interviewerUser);
    Interviewer interviewer = new Interviewer(interviewerUser, 3, null);
    interviewerService.registerInterviewer(interviewer);

    mockMvc.perform(delete("/users/interviewers/{interviewerId}", interviewerUser.getId()))
        .andExpect(status().isOk());

    Assertions.assertEquals(Role.CANDIDATE,
        userService.findUserById(interviewerUser.getId()).getRole());
  }

  @Test
  @Order(7)
  void grantInterviewerRoleTest() throws Exception {
    userService.registerUser(new User("example100@gmail.com", Role.COORDINATOR));

    mockMvc.perform(post("/users/interviewers")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\":\"example100@gmail.com\"}")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    Assertions.assertEquals(Role.INTERVIEWER,
        userService.findUserByEmail("example100@gmail.com").getRole());
  }

  @Test
  @Order(8)
  void grantInterviewerRoleTest_whenAlreadyInterviewer() throws Exception {
    User user = new User("example100@gmail.com", Role.INTERVIEWER);
    userService.registerUser(user);

    try {
      user.setRole(Role.INTERVIEWER);
    } catch (SameRoleChangeException e) {
      assertNotNull(e);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  @Order(9)
  void createBookingTestWithCorrectData() throws Exception {
    InterviewerSlot interviewerSlot = new InterviewerSlot(203951, 4, LocalTime.of(9, 0),
        LocalTime.of(21, 0)
    );
    interviewerSlot.setInterviewer(INTERVIEWERS.get(0));

    CandidateSlot candidateSlot = new CandidateSlot(
        LocalDateTime.of(LocalDate.of(2039, Month.DECEMBER, 22), LocalTime.of(9, 30)),
        LocalDateTime.of(LocalDate.of(2039, Month.DECEMBER, 22), LocalTime.of(21, 0))
    );
    candidateSlot.setCandidate(CANDIDATES.get(0));

    interviewerService.registerSlot(interviewerSlot);
    candidateService.registerSlot(candidateSlot);

    String bookingJson = "{"
        + "    \"dateFrom\": \"2039-12-22 14:30\","
        + "    \"dateTo\": \"2039-12-22 16:00\","
        + "    \"subject\": \"subject\","
        + "    \"description\": \"test description\","
        + "    \"status\": 0,"
        + "    \"candidateSlotId\": " + candidateSlot.getId() + ","
        + "    \"interviewerSlotId\": " + interviewerSlot.getId()
        + "}";

    String bookingJsonResponse = mockMvc.perform(post("/bookings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(bookingJson)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    JSONObject jsonObject = new JSONObject(bookingJsonResponse);
    Assertions.assertDoesNotThrow(() -> bookingService.getBookingById(jsonObject.getLong("id")));
    Assertions.assertEquals("test description", jsonObject.getString("description"));
  }

  @Test
  @Order(10)
  void updateBookingCorrectData() throws Exception {
    String bookingJson = "{"
        + "    \"dateFrom\": \"2039-12-22 16:30\","
        + "    \"dateTo\": \"2039-12-22 18:00\","
        + "    \"subject\": \"subject\","
        + "    \"description\": \"test description\","
        + "    \"status\": 0,"
        + "    \"candidateSlotId\": 2,"
        + "    \"interviewerSlotId\": 2"
        + "}";

    mockMvc.perform(post("/bookings/2")
            .contentType(MediaType.APPLICATION_JSON)
            .content(bookingJson)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.dateFrom", equalTo("2039-12-22 16:30")))
        .andExpect(jsonPath("$.dateTo", equalTo("2039-12-22 18:00")));
  }

  @Test
  @Order(11)
  void updateBookingWithIncorrectTime() throws Exception {
    String bookingJson = "{"
        + "    \"dateFrom\": \"2039-12-22 17:30\","
        + "    \"dateTo\": \"2039-12-22 18:00\","
        + "    \"subject\": \"subject\","
        + "    \"description\": \"test description\","
        + "    \"status\": 0,"
        + "    \"candidateSlotId\": 2,"
        + "    \"interviewerSlotId\": 2"
        + "}";

    mockMvc.perform(post("/bookings/2")
            .contentType(MediaType.APPLICATION_JSON)
            .content(bookingJson)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.errorCode", equalTo("invalid_booking_boundaries")))
        .andExpect(jsonPath("$.errorMessage", equalTo(
            "Booking duration have to be 1.5 hours and it have to be registered in future.")));
  }

  @Test
  @Order(12)
  void updateBookingWhenOutOfSlot() throws Exception {
    String bookingJson = "{"
        + "    \"dateFrom\": \"2039-12-23 16:30\","
        + "    \"dateTo\": \"2039-12-23 18:00\","
        + "    \"subject\": \"subject\","
        + "    \"description\": \"test description\","
        + "    \"status\": 0,"
        + "    \"candidateSlotId\": 2,"
        + "    \"interviewerSlotId\": 2"
        + "}";

    mockMvc.perform(post("/bookings/2")
            .contentType(MediaType.APPLICATION_JSON)
            .content(bookingJson)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.errorCode", equalTo("booking_out_of_slot")))
        .andExpect(jsonPath("$.errorMessage", equalTo(
            "This booking is out of slot boundaries.")));
  }


  @Test
  @Order(13)
  void getDashboardTest() throws Exception {
    int referentDayOfWeek = 2;
    int referentWeekNum = weekService.getNextWeekNum();
    LocalDateTime referentLDT = LocalDateTime.of(
        weekService.getDateFromWeekAndDay(referentWeekNum, referentDayOfWeek),
        LocalTime.of(0, 0));

    CandidateSlot candidateSlot = new CandidateSlot(
        referentLDT.plusHours(10), referentLDT.plusHours(18));
    candidateSlot.setCandidate(CANDIDATES.get(0));
    candidateService.registerSlot(candidateSlot);

    InterviewerSlot interviewerSlot = new InterviewerSlot(referentWeekNum, referentDayOfWeek,
        LocalTime.of(8, 0), LocalTime.of(15, 0));
    interviewerSlot.setInterviewer(INTERVIEWERS.get(0));
    interviewerService.registerSlot(interviewerSlot);

    LocalDateTime bookingLDT = referentLDT.plusHours(11);
    Booking booking = new Booking(bookingLDT, bookingLDT.plusMinutes(90),
        "subject", "description", Status.NEW);
    booking.setCandidateSlot(candidateSlot);
    booking.setInterviewerSlot(interviewerSlot);
    bookingService.registerBooking(booking);
    int bookingId = booking.getId().intValue();

    mockMvc.perform(get("/weeks/{weekId}/dashboard", referentWeekNum))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(7)))
        .andExpect(jsonPath("$[1].day",
            equalTo(referentLDT.format(DateTimeFormatter.ISO_DATE))))
        .andExpect(jsonPath("$[4].day",
            equalTo(referentLDT.plusDays(3).format(DateTimeFormatter.ISO_DATE))))
        .andExpect(jsonPath("$[1].interviewerSlotFormsWithId.[0].interviewerSlotDto.timeFrom",
            equalTo(interviewerSlot.getFrom().format(DateTimeFormatter.ISO_TIME).substring(0, 5))))
        .andExpect(jsonPath("$[1].interviewerSlotFormsWithId[0].interviewerSlotDto.interviewerId",
            equalTo(INTERVIEWERS.get(0).getId().intValue())))
        .andExpect(jsonPath("$[1].interviewerSlotFormsWithId[0].bookingsId",
            contains(bookingId)))
        .andExpect(jsonPath("$[1].candidateSlotsFormsWithId[0].candidateSlotDto.candidateId",
            equalTo(CANDIDATES.get(0).getId().intValue())))
        .andExpect(jsonPath("$[1].candidateSlotsFormsWithId[0].bookingsId",
            contains(bookingId)))
        .andExpect(jsonPath("$[1].bookings." + bookingId + ".interviewerSlotId",
            equalTo(interviewerSlot.getId().intValue())))
        .andExpect(jsonPath("$[1].bookings." + bookingId + ".candidateSlotId",
            equalTo(candidateSlot.getId().intValue())));
  }
}


