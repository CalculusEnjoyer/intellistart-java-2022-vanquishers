package com.intellias.intellistart.interviewplanning.controllers;

import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import com.intellias.intellistart.interviewplanning.models.Interviewer;
import com.intellias.intellistart.interviewplanning.models.User;
import com.intellias.intellistart.interviewplanning.models.enums.Role;
import com.intellias.intellistart.interviewplanning.services.CandidateService;
import com.intellias.intellistart.interviewplanning.services.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class CandidateControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private UserService userService;

  @Autowired
  private CandidateService candidateService;

  private static final int YEAR = LocalDate.now().getYear() + 1;

  private static final List<User> users = List.of(
      new User("email1@test.com", Role.INTERVIEWER),
      new User("email2@test.com", Role.INTERVIEWER)
  );

  private final List<Interviewer> interviewers = List.of(
      new Interviewer(users.get(0), 5, null),
      new Interviewer(users.get(1), 7, null)
  );

  private final List<CandidateSlot> slots = List.of(
      new CandidateSlot(
          LocalDateTime.of(LocalDate.of(YEAR, 10, 12), LocalTime.of(9, 30)),
          LocalDateTime.of(LocalDate.of(YEAR, 10, 12), LocalTime.of(11, 0))
      ),
      new CandidateSlot(
          LocalDateTime.of(LocalDate.of(YEAR, 10, 13), LocalTime.of(9, 30)),
          LocalDateTime.of(LocalDate.of(YEAR, 10, 13), LocalTime.of(11, 0))
      )
  );

}
