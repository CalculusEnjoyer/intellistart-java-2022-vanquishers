package com.intellias.intellistart.interviewplanning.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.intellias.intellistart.interviewplanning.models.Candidate;
import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import com.intellias.intellistart.interviewplanning.models.User;
import com.intellias.intellistart.interviewplanning.models.enums.Role;
import com.intellias.intellistart.interviewplanning.models.security.FacebookUserDetails;
import com.intellias.intellistart.interviewplanning.services.CandidateService;
import com.intellias.intellistart.interviewplanning.services.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
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
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@WithMockUser(roles={"CANDIDATE"})
class CandidateControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private UserService userService;

  @Autowired
  private CandidateService candidateService;

  private static final int NEXT_YEAR = LocalDate.now().getYear() + 1;

  private static final List<User> USERS = List.of(
      new User("email1@test.com", Role.CANDIDATE),
      new User("email2@test.com", Role.CANDIDATE)
  );

  private static final List<Candidate> CANDIDATES = List.of(
      new Candidate(new HashSet<>(), USERS.get(0)),
      new Candidate(new HashSet<>(), USERS.get(1))
  );

  private static final List<CandidateSlot> SLOTS = List.of(
      new CandidateSlot(
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, 10, 12), LocalTime.of(9, 30)),
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, 10, 12), LocalTime.of(11, 0))
      ),
      new CandidateSlot(
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, 10, 13), LocalTime.of(9, 30)),
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, 10, 13), LocalTime.of(11, 0))
      ),
      new CandidateSlot(
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, 10, 14), LocalTime.of(9, 30)),
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, 10, 14), LocalTime.of(11, 0))
      ),
      new CandidateSlot(
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, 10, 15), LocalTime.of(9, 30)),
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, 10, 15), LocalTime.of(11, 0))
      )
  );

  @BeforeEach
  void setupBeforeEach() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

    USERS.forEach(u -> userService.registerUser(u));
    CANDIDATES.forEach(i -> candidateService.registerCandidate(i));

    SLOTS.get(0).setCandidate(CANDIDATES.get(0));
    SLOTS.stream().skip(1).forEach(e -> e.setCandidate(CANDIDATES.get(1)));

    SLOTS.forEach(slot -> candidateService.registerSlot(slot));
  }

  @Test
  @Order(0)
  void isValidAppContextTest() {
    ServletContext servletContext = webApplicationContext.getServletContext();

    assertThat(servletContext).isInstanceOf(MockServletContext.class);
    assertThat(webApplicationContext.getBean("candidateController")).isNotNull();
  }

  /**
   * Check user[0] creating slot with correct data.
   */
  @Test
  @Order(1)
  void addSlotTest_whenCorrectData() throws Exception {
    String url = "/candidates/current/slots";
    Authentication auth = authenticateUser(USERS.get(0));
    String slotDtoJsonStr = constructSlotDtoAsString(
        "2024-06-11 14:00", "2024-06-11 16:00");
    int slotCountBeforeAdd = candidateService.getAllSlots().size();

    mockMvc.perform(post(url)
            .contentType(APPLICATION_JSON)
            .content(slotDtoJsonStr)
            .principal(auth))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.dateFrom", equalTo("2024-06-11 14:00")))
        .andExpect(jsonPath("$.dateTo", equalTo("2024-06-11 16:00")));
    int slotCountAfterAdd = candidateService.getAllSlots().size();

    assertThat(slotCountAfterAdd).isEqualTo(slotCountBeforeAdd + 1);
  }

  /**
   * Check user[0] creating slot with overlapping.
   */
  @Test
  @Order(2)
  void addSlotTest_whenOverlappingSlot() throws Exception {
    String url = "/candidates/current/slots";
    Authentication auth = authenticateUser(USERS.get(0));
    String slotDtoJsonStr = constructSlotDtoAsString(
        "2024-06-11 14:00", "2024-06-11 16:00");
    int slotCountBeforeAdd = candidateService.getAllSlots().size();

    mockMvc.perform(post(url)
            .contentType(APPLICATION_JSON)
            .content(slotDtoJsonStr)
            .principal(auth))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode", equalTo("slot_is_overlapping")));
    int slotCountAfterAdd = candidateService.getAllSlots().size();

    assertThat(slotCountAfterAdd).isEqualTo(slotCountBeforeAdd);
  }

  /**
   * Check user[0] creating slot with wrong data.
   */
  @Test
  @Order(2)
  void addSlotTest_whenWrongData() throws Exception {
    String url = "/candidates/current/slots";
    Authentication auth = authenticateUser(USERS.get(0));
    String slotDtoJsonStr = constructSlotDtoAsString(
        "woah its a", "wrong data");
    int slotCountBeforeAdd = candidateService.getAllSlots().size();

    mockMvc.perform(post(url)
            .contentType(APPLICATION_JSON)
            .content(slotDtoJsonStr)
            .principal(auth))
        .andDo(print())
        .andExpect(status().isBadRequest());
    int slotCountAfterAdd = candidateService.getAllSlots().size();

    assertThat(slotCountAfterAdd).isEqualTo(slotCountBeforeAdd);
  }

  /**
   * Check user[0] creating slot with wrong bounds.
   */
  @Test
  @Order(2)
  void addSlotTest_whenWrongBounds() throws Exception {
    String url = "/candidates/current/slots";
    Authentication auth = authenticateUser(USERS.get(0));
    String slotDtoJsonStr = constructSlotDtoAsString(
        "2024-06-12 14:00", "2024-06-12 14:30");
    int slotCountBeforeAdd = candidateService.getAllSlots().size();

    mockMvc.perform(post(url)
            .contentType(APPLICATION_JSON)
            .content(slotDtoJsonStr)
            .principal(auth))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode", equalTo("invalid_boundaries")));
    int slotCountAfterAdd = candidateService.getAllSlots().size();

    assertThat(slotCountAfterAdd).isEqualTo(slotCountBeforeAdd);
  }

  /**
   * Check users GET same to their candidate linked slots.
   */
  @Test
  @Order(3)
  void getSlotTest_forUsers() throws Exception {
    String url = "/candidates/current/slots";
    Authentication auth0 = authenticateUser(USERS.get(0));
    Authentication auth1 = authenticateUser(USERS.get(1));
    List<CandidateSlot> slots;

    slots = new ArrayList<>(
        candidateService.getCandidateByUserId(USERS.get(0).getId()).getCandidateSlot());

    mockMvc.perform(get(url).principal(auth0))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].id", contains(
            slots.stream()
                .map(CandidateSlot::getId)
                .map(Long::intValue)
                .toArray()
        )))
        .andExpect(jsonPath("$[*].dateFrom", contains(
            slots.stream()
                .map(CandidateSlot::getDateFrom)
                .map(Object::toString)
                .map(e -> e.replace('T', ' '))
                .toArray()
        )))
        .andExpect(jsonPath("$[*].dateTo", contains(
            slots.stream()
                .map(CandidateSlot::getDateTo)
                .map(Object::toString)
                .map(e -> e.replace('T', ' '))
                .toArray()
        )));


    slots = new ArrayList<>(
        candidateService.getCandidateByUserId(USERS.get(1).getId()).getCandidateSlot());

    mockMvc.perform(get(url).principal(auth1))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].id", contains(
            slots.stream()
                .map(CandidateSlot::getId)
                .map(Long::intValue)
                .toArray()
        )))
        .andExpect(jsonPath("$[*].dateFrom", contains(
            slots.stream()
                .map(CandidateSlot::getDateFrom)
                .map(Object::toString)
                .map(e -> e.replace('T', ' '))
                .toArray()
        )))
        .andExpect(jsonPath("$[*].dateTo", contains(
            slots.stream()
                .map(CandidateSlot::getDateTo)
                .map(Object::toString)
                .map(e -> e.replace('T', ' '))
                .toArray()
        )));
  }

  /**
   * Check user[0] updating slot with correct data.
   */
  @Test
  @Order(4)
  void updateSlotTest_whenCorrectData() throws Exception {
    String url = "/candidates/current/slots/1";
    Authentication auth = authenticateUser(USERS.get(0));
    String slotDtoJsonStr = constructSlotDtoAsString(
        "2024-06-11 18:00", "2024-06-11 20:00");
    int slotCountBeforeAdd = candidateService.getAllSlots().size();

    mockMvc.perform(post(url)
            .contentType(APPLICATION_JSON)
            .content(slotDtoJsonStr)
            .principal(auth))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.dateFrom", equalTo("2024-06-11 18:00")))
        .andExpect(jsonPath("$.dateTo", equalTo("2024-06-11 20:00")));
    int slotCountAfterAdd = candidateService.getAllSlots().size();

    assertThat(slotCountAfterAdd).isEqualTo(slotCountBeforeAdd);
  }

  /**
   * Check user[0] updating slot with overlapping.
   */
  @Test
  @Order(5)
  void updateSlotTest_whenOverlappingSlot() throws Exception {
    String url = "/candidates/current/slots/1";
    Authentication auth = authenticateUser(USERS.get(0));
    String slotDtoJsonStr = constructSlotDtoAsString(
        "2024-06-11 15:00", "2024-06-11 17:00");
    int slotCountBeforeAdd = candidateService.getAllSlots().size();

    mockMvc.perform(post(url)
            .contentType(APPLICATION_JSON)
            .content(slotDtoJsonStr)
            .principal(auth))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode", equalTo("slot_is_overlapping")));
    int slotCountAfterAdd = candidateService.getAllSlots().size();

    assertThat(slotCountAfterAdd).isEqualTo(slotCountBeforeAdd);
  }

  /**
   * Check user[0] updating slot with wrong data.
   */
  @Test
  @Order(5)
  void updateSlotTest_whenWrongData() throws Exception {
    String url = "/candidates/current/slots/1";
    Authentication auth = authenticateUser(USERS.get(0));
    String slotDtoJsonStr = constructSlotDtoAsString(
        "woah its a", "wrong data");
    int slotCountBeforeAdd = candidateService.getAllSlots().size();

    mockMvc.perform(post(url)
            .contentType(APPLICATION_JSON)
            .content(slotDtoJsonStr)
            .principal(auth))
        .andDo(print())
        .andExpect(status().isBadRequest());
    int slotCountAfterAdd = candidateService.getAllSlots().size();

    assertThat(slotCountAfterAdd).isEqualTo(slotCountBeforeAdd);
  }

  /**
   * Check user[0] updating slot with wrong bounds.
   */
  @Test
  @Order(5)
  void updateSlotTest_whenWrongBounds() throws Exception {
    String url = "/candidates/current/slots/1";
    Authentication auth = authenticateUser(USERS.get(0));
    String slotDtoJsonStr = constructSlotDtoAsString(
        "2024-06-12 14:00", "2024-06-12 14:30");
    int slotCountBeforeAdd = candidateService.getAllSlots().size();

    mockMvc.perform(post(url)
            .contentType(APPLICATION_JSON)
            .content(slotDtoJsonStr)
            .principal(auth))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode", equalTo("invalid_boundaries")));
    int slotCountAfterAdd = candidateService.getAllSlots().size();

    assertThat(slotCountAfterAdd).isEqualTo(slotCountBeforeAdd);
  }

  /**
   * Check user[1] illegal access for user[0] update.
   */
  @Test
  @Order(5)
  void updateSlotTest_whenIllegalAccess() throws Exception {
    String url = "/candidates/current/slots/1";
    Authentication auth = authenticateUser(USERS.get(1));
    String slotDtoJsonStr = constructSlotDtoAsString(
        "2024-06-11 14:00", "2024-06-11 16:30");
    int slotCountBeforeAdd = candidateService.getAllSlots().size();

    mockMvc.perform(post(url)
            .contentType(APPLICATION_JSON)
            .content(slotDtoJsonStr)
            .principal(auth))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode", equalTo("slot_access_error")));
    int slotCountAfterAdd = candidateService.getAllSlots().size();

    assertThat(slotCountAfterAdd).isEqualTo(slotCountBeforeAdd);
  }

  /**
   * Check users GET same to their candidate linked slots.
   */
  @Test
  @Order(6)
  void getSlotTest_forUsers_afterUpdate() throws Exception {
    String url = "/candidates/current/slots";
    Authentication auth0 = authenticateUser(USERS.get(0));
    Authentication auth1 = authenticateUser(USERS.get(1));
    List<CandidateSlot> slots;

     slots = new ArrayList<>(
        candidateService.getCandidateByUserId(USERS.get(0).getId()).getCandidateSlot());

    mockMvc.perform(get(url).principal(auth0))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].id", contains(
            slots.stream()
                .map(CandidateSlot::getId)
                .map(Long::intValue)
                .toArray()
        )))
        .andExpect(jsonPath("$[*].dateFrom", contains(
            slots.stream()
                .map(CandidateSlot::getDateFrom)
                .map(Object::toString)
                .map(e -> e.replace('T', ' '))
                .toArray()
        )))
        .andExpect(jsonPath("$[*].dateTo", contains(
            slots.stream()
                .map(CandidateSlot::getDateTo)
                .map(Object::toString)
                .map(e -> e.replace('T', ' '))
                .toArray()
        )));


    slots = new ArrayList<>(
        candidateService.getCandidateByUserId(USERS.get(1).getId()).getCandidateSlot());

    mockMvc.perform(get(url).principal(auth1))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].id", contains(
            slots.stream()
                .map(CandidateSlot::getId)
                .map(Long::intValue)
                .toArray()
        )))
        .andExpect(jsonPath("$[*].dateFrom", contains(
            slots.stream()
                .map(CandidateSlot::getDateFrom)
                .map(Object::toString)
                .map(e -> e.replace('T', ' '))
                .toArray()
        )))
        .andExpect(jsonPath("$[*].dateTo", contains(
            slots.stream()
                .map(CandidateSlot::getDateTo)
                .map(Object::toString)
                .map(e -> e.replace('T', ' '))
                .toArray()
        )));
  }

  private String constructSlotDtoAsString(String fromTime, String toTime) {
    return String.format(
        "{\"dateFrom\": \"%s\",\"dateTo\": \"%s\"}", fromTime, toTime);
  }

  private Authentication authenticateUser(User user) {
    FacebookUserDetails fbUD = new FacebookUserDetails(user);
    return new UsernamePasswordAuthenticationToken(fbUD, null, fbUD.getAuthorities());
  }

}
