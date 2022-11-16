package com.intellias.intellistart.interviewplanning.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.intellias.intellistart.interviewplanning.models.Interviewer;
import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.models.User;
import com.intellias.intellistart.interviewplanning.models.enums.Role;
import com.intellias.intellistart.interviewplanning.services.InterviewerService;
import com.intellias.intellistart.interviewplanning.services.UserService;
import com.intellias.intellistart.interviewplanning.services.WeekService;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc(addFilters = false)
class InterviewerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private UserService userService;

  @Autowired
  private InterviewerService interviewerService;

  private static final List<User> USERS = List.of(
      new User(1L, "email1@test.com", Role.INTERVIEWER),
      new User(2L, "email2@test.com", Role.INTERVIEWER)
  );

  private static final List<Interviewer> INTERVIEWERS = List.of(
      new Interviewer(USERS.get(0), 5, null),
      new Interviewer(USERS.get(1), 7, null)
  );

  private static final List<InterviewerSlot> SLOTS = List.of(
      new InterviewerSlot(null, 0, 1,
          LocalTime.of(9, 30), LocalTime.of(11, 0),
          INTERVIEWERS.get(0), null),
      new InterviewerSlot(null, 0, 1,
          LocalTime.of(10, 30), LocalTime.of(12, 0),
          INTERVIEWERS.get(0), null),
      new InterviewerSlot(null, 0, 4,
          LocalTime.of(13, 30), LocalTime.of(19, 0),
          INTERVIEWERS.get(1), null)
  );

  //to minimize the chance of test failure because the tests were performed at the time
  //of week change, the referent week nums are initialized upon test start
  //instead of calling weekService.get[Current or Next]WeekNum() in the tests
  private final int CURRENT_WEEK_NUM;
  private final int NEXT_WEEK_NUM;

  @Autowired
  InterviewerControllerTest(WeekService weekService) {
    CURRENT_WEEK_NUM = weekService.getCurrentWeekNum();
    NEXT_WEEK_NUM = weekService.getNextWeekNum();
  }

  @BeforeEach
  void setupBeforeEach() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

    USERS.forEach(u -> userService.register(u));
    INTERVIEWERS.forEach(i -> interviewerService.registerInterviewer(i));

    SLOTS.get(0).setWeekNum(CURRENT_WEEK_NUM);
    SLOTS.get(1).setWeekNum(NEXT_WEEK_NUM);
    SLOTS.get(2).setWeekNum(NEXT_WEEK_NUM);
    SLOTS.forEach(slot -> interviewerService.registerSlot(slot));
  }

  @Test
  @Order(0)
  void isValidAppContextTest() {
    ServletContext servletContext = webApplicationContext.getServletContext();

    assertThat(servletContext).isInstanceOf(MockServletContext.class);
    assertThat(webApplicationContext.getBean("interviewerController")).isNotNull();
  }

  @Test
  @Order(1)
  void addSlotTest_whenCorrectData() throws Exception {
    String url = "/interviewers/{interviewerId}/slots";
    Long interviewerId = INTERVIEWERS.get(0).getId();
    String slotDtoJsonStr = constructSlotDtoAsString(
        NEXT_WEEK_NUM, 5, "10:00", "15:30");
    int slotCountBeforeAdd = interviewerService.getAllSlots().size();

    mockMvc.perform(post(url, interviewerId).contentType(APPLICATION_JSON)
            .content(slotDtoJsonStr))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.weekNum", equalTo(NEXT_WEEK_NUM)))
        .andExpect(jsonPath("$.interviewerId", equalTo(interviewerId.intValue())));
    int slotCountAfterAdd = interviewerService.getAllSlots().size();
    InterviewerSlot savedSlot = Collections.max(
        interviewerService.getSlotsForIdAndWeek(interviewerId, NEXT_WEEK_NUM),
        Comparator.comparing(InterviewerSlot::getId));

    assertThat(slotCountAfterAdd).isEqualTo(slotCountBeforeAdd + 1);
    assertThat(savedSlot.getDayOfWeek()).isEqualTo(5);
    assertThat(savedSlot.getFrom()).isEqualTo(LocalTime.of(10, 0));
    assertThat(savedSlot.getTo()).isEqualTo(LocalTime.of(15, 30));
  }

  @Test
  @Order(2)
  void addSlotTest_whenInvalidBoundaries() throws Exception {
    String url = "/interviewers/{interviewerId}/slots";
    Long interviewerId = INTERVIEWERS.get(0).getId();
    String slotDtoJsonStr = constructSlotDtoAsString(
        NEXT_WEEK_NUM, 5, "06:00", "15:49");
    int slotCountBeforeAdd = interviewerService.getAllSlots().size();

    mockMvc.perform(post(url, interviewerId).contentType(APPLICATION_JSON)
            .content(slotDtoJsonStr)
            .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode", equalTo("invalid_boundaries")))
        .andExpect(jsonPath("$.errorMessage",
            equalTo("Invalid slot time boundaries")));
    int slotCountAfterAdd = interviewerService.getAllSlots().size();

    assertThat(slotCountAfterAdd).isEqualTo(slotCountBeforeAdd);
  }

  @Test
  @Order(3)
  void updateSlotTest_whenCorrectData() throws Exception {
    String url = "/interviewers/{interviewerId}/slots/{slotId}";
    Long interviewerId = INTERVIEWERS.get(0).getId();
    Long slotId = SLOTS.get(1).getId();
    String slotDtoJsonStr = constructSlotDtoAsString(
        NEXT_WEEK_NUM, 3, "08:00", "17:30");
    int slotCountBeforeAdd = interviewerService.getAllSlots().size();

    mockMvc.perform(post(url, interviewerId, slotId).contentType(APPLICATION_JSON)
            .content(slotDtoJsonStr)
            .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.weekNum", equalTo(NEXT_WEEK_NUM)))
        .andExpect(jsonPath("$.interviewerId", equalTo(interviewerId.intValue())));
    int slotCountAfterAdd = interviewerService.getAllSlots().size();
    InterviewerSlot slot = interviewerService.getSlotById(slotId);

    assertThat(slotCountAfterAdd).isEqualTo(slotCountBeforeAdd);
    assertThat(slot.getDayOfWeek()).isEqualTo(3);
    assertThat(slot.getFrom()).isEqualTo(LocalTime.of(8, 0));
    assertThat(slot.getTo()).isEqualTo(LocalTime.of(17, 30));
  }

  @Test
  @Order(4)
  void updateSlotTest_whenInvalidBoundaries() throws Exception {
    String url = "/interviewers/{interviewerId}/slots/{slotId}";
    Long interviewerId = INTERVIEWERS.get(0).getId();
    Long slotId = SLOTS.get(1).getId();
    String slotDtoJsonStr = constructSlotDtoAsString(
        NEXT_WEEK_NUM, 5, "05:00", "15:19");
    int slotCountBeforeAdd = interviewerService.getAllSlots().size();

    mockMvc.perform(post(url, interviewerId, slotId).contentType(APPLICATION_JSON)
            .content(slotDtoJsonStr))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode", equalTo("invalid_boundaries")))
        .andExpect(jsonPath("$.errorMessage",
            equalTo("Invalid slot time boundaries")));
    int slotCountAfterAdd = interviewerService.getAllSlots().size();
    InterviewerSlot slot = interviewerService.getSlotById(slotId);

    assertThat(slotCountAfterAdd).isEqualTo(slotCountBeforeAdd);
    assertThat(slot.getDayOfWeek()).isEqualTo(SLOTS.get(1).getDayOfWeek());
    assertThat(slot.getFrom()).isEqualTo(SLOTS.get(1).getFrom());
    assertThat(slot.getTo()).isEqualTo(SLOTS.get(1).getTo());
  }

  @Test
  @Order(5)
  void getCurrentWeekSlotTest() throws Exception {
    String url = "/interviewers/{interviewerId}/slots/current-week";
    Long interviewerId = INTERVIEWERS.get(0).getId();
    int expectedSize = interviewerService
        .getSlotsForIdAndWeek(interviewerId, CURRENT_WEEK_NUM).size();

    mockMvc.perform(get(url, interviewerId))
        .andDo(print())
        .andExpect(jsonPath("$", hasSize(expectedSize)))
        .andExpect(jsonPath("$[0].weekNum", equalTo(CURRENT_WEEK_NUM)));
  }

  @Test
  @Order(6)
  void getNextWeekSlotTest() throws Exception {
    String url = "/interviewers/{interviewerId}/slots/next-week";
    Long interviewerId = INTERVIEWERS.get(0).getId();
    int expectedSize = interviewerService
        .getSlotsForIdAndWeek(interviewerId, NEXT_WEEK_NUM).size();

    mockMvc.perform(get(url, interviewerId))
        .andDo(print())
        .andExpect(jsonPath("$", hasSize(expectedSize)))
        .andExpect(jsonPath("$[0].weekNum", equalTo(NEXT_WEEK_NUM)));
  }

  @Test
  @Order(7)
  void setBookingLimitTest_whenCorrectBookingLimit() throws Exception {
    String url = "/interviewers/{interviewerId}/bookings/booking-limit";
    Long interviewerId = INTERVIEWERS.get(0).getId();
    int bookingLimitExpected = 99;

    mockMvc.perform(post(url, interviewerId).contentType(APPLICATION_JSON)
            .content("{\"bookingLimit\": 99}"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.bookingLimit", equalTo(99)));

    int bookingLimitActual = interviewerService
        .getInterviewerById(interviewerId).getBookingLimit();

    assertThat(bookingLimitActual).isEqualTo(bookingLimitExpected);
  }

  @Test
  @Order(8)
  void setBookingLimitTest_whenInvalidBookingLimit() throws Exception {
    String url = "/interviewers/{interviewerId}/bookings/booking-limit";
    Long interviewerId = INTERVIEWERS.get(0).getId();

    int bookingLimitBeforeCall = interviewerService
        .getInterviewerById(interviewerId).getBookingLimit();

    mockMvc.perform(post(url, interviewerId).contentType(APPLICATION_JSON)
            .content("{\"bookingLimit\": -1}"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode", equalTo("bad_request")));

    int bookingLimitAfterCall = interviewerService
        .getInterviewerById(interviewerId).getBookingLimit();

    assertThat(bookingLimitBeforeCall).isEqualTo(bookingLimitAfterCall);
  }

  private static String constructSlotDtoAsString(
      int weekNum, int dayOfWeek, String from, String to) {
    return String.format(
        "{\"weekNum\": %d,\"dayOfWeek\": %d,\"timeFrom\": \"%s\",\"timeTo\": \"%s\"}",
        weekNum, dayOfWeek, from, to);
  }
}
