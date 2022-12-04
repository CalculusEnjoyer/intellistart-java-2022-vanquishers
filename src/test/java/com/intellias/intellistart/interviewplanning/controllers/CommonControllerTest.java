package com.intellias.intellistart.interviewplanning.controllers;


import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.intellias.intellistart.interviewplanning.models.User;
import com.intellias.intellistart.interviewplanning.models.enums.Role;
import com.intellias.intellistart.interviewplanning.models.security.FacebookUserDetails;
import com.intellias.intellistart.interviewplanning.services.UserService;
import com.intellias.intellistart.interviewplanning.services.WeekService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class CommonControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserService userService;

  @Autowired
  private WeekService weekService;

  @Test
  void getCurrentWeekNumTest() throws Exception {
    String url = "/weeks/current";

    int expectedWeekNum = weekService.getCurrentWeekNum();
    mockMvc.perform(get(url).contentType(APPLICATION_JSON)
            .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", equalTo(expectedWeekNum)));
  }

  @Test
  void getNextWeekNumTest() throws Exception {
    String url = "/weeks/next";

    int expectedWeekNum = weekService.getNextWeekNum();
    mockMvc.perform(get(url).contentType(APPLICATION_JSON)
            .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", equalTo(expectedWeekNum)));
  }

  @Test
  @WithMockUser(roles={"INTERVIEWER"})
  void getMeTest_whenCorrectAuthorization() throws Exception {
    String email = "emailgetmetestcorrect@gmail.com";
    User user = new User(email, Role.INTERVIEWER);
    userService.registerUser(user);

    mockMvc.perform(get("/me").contentType(APPLICATION_JSON)
            .characterEncoding("utf-8")
            .principal(getAuthForUser(user)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", equalTo(user.getId().intValue())))
        .andExpect(jsonPath("$.email", equalTo(email)))
        .andExpect(jsonPath("$.role", equalTo("INTERVIEWER")));
  }

  private Authentication getAuthForUser(User user) {
    FacebookUserDetails FbUD = new FacebookUserDetails(user);
    return new UsernamePasswordAuthenticationToken(FbUD, null, FbUD.getAuthorities());
  }
}
