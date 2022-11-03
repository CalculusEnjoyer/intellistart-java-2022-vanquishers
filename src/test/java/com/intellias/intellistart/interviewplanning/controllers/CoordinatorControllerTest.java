package com.intellias.intellistart.interviewplanning.controllers;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.intellias.intellistart.interviewplanning.models.Booking;
import com.intellias.intellistart.interviewplanning.models.Interviewer;
import com.intellias.intellistart.interviewplanning.models.User;
import com.intellias.intellistart.interviewplanning.models.enums.Role;
import com.intellias.intellistart.interviewplanning.models.enums.Status;
import com.intellias.intellistart.interviewplanning.repositories.BookingRepository;
import com.intellias.intellistart.interviewplanning.services.BookingService;
import com.intellias.intellistart.interviewplanning.services.InterviewerService;
import com.intellias.intellistart.interviewplanning.services.UserService;
import com.intellias.intellistart.interviewplanning.util.exceptions.BookingNotFoundException;
import java.time.LocalDateTime;
import java.time.Month;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
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
  private BookingService bookingService;

  @Autowired
  private UserService userService;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before("")
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    userService.register(new User(1L, "email1", Role.INTERVIEWER));
    userService.register(new User(2L, "email2", Role.INTERVIEWER));
    userService.register(new User(3L, "email3", Role.COORDINATOR));
    userService.register(new User(4L, "email4", Role.COORDINATOR));
    interviewerService.registerInterviewer(new Interviewer(userService.findUserById(1L),5,null));
    interviewerService.registerInterviewer(new Interviewer(userService.findUserById(2L),2,null));
  }

  @Test
  @Order(1)
  void deleteBookingTest() throws Exception {
    Booking booking = new Booking(LocalDateTime.of(2015,
        Month.JULY, 29, 19, 30), LocalDateTime.of(2015,
        Month.JULY, 29, 21, 30), "check", "check", Status.BOOKED);
    bookingService.registerBooking(booking);
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
    User coordinator = new User();
    coordinator.setRole(Role.COORDINATOR);
    coordinator.setFacebookId(13213L);
    coordinator.setEmail("test@email.com");
    userService.register(coordinator);

    mockMvc.perform(delete("/users/coordinators/{coordinatorId}", coordinator.getId()))
        .andExpect(status().isOk());

    Assertions.assertEquals(Role.CANDIDATE,
        userService.findUserById(coordinator.getId()).getRole());
  }

  @Test
  @Order(3)
  void getCoordinatorsTest() throws Exception {
    mockMvc.perform(
            MockMvcRequestBuilders.get("/users/coordinators").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(userService.findAllUsersByRole(Role.COORDINATOR).size())));
  }

  @Test
  @Order(4)
  void grantCoordinatorRoleTest() throws Exception {
    userService.register(new User(123L, "example34@gmail.com", Role.INTERVIEWER));

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
        .andExpect(jsonPath("$", hasSize(interviewerService.getAllInterviewers().size())));
  }
}
