package com.intellias.intellistart.interviewplanning.auth;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.intellias.intellistart.interviewplanning.config.JwtConfig;
import com.intellias.intellistart.interviewplanning.config.JwtTokenAuthenticationFilter;
import com.intellias.intellistart.interviewplanning.controllers.AuthController;
import com.intellias.intellistart.interviewplanning.models.security.FacebookUser;
import com.intellias.intellistart.interviewplanning.payload.FacebookLoginRequest;
import com.intellias.intellistart.interviewplanning.payload.JwtAuthenticationResponse;
import com.intellias.intellistart.interviewplanning.services.FacebookClient;
import com.intellias.intellistart.interviewplanning.services.FacebookService;
import com.intellias.intellistart.interviewplanning.services.JwtTokenProvider;
import com.intellias.intellistart.interviewplanning.util.exceptions.InvalidJwtTokenException;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ExtendWith(OutputCaptureExtension.class)
class AuthenticationTest {

  @Autowired
  private FacebookClient facebookClient;

  @Autowired
  private FacebookService facebookService;

  private final String jwtToken = "EAALXaskmJ0ABALd1Ig5KUIKpZCor5UjnwmgHj1R08J4qprqoV2rFlfvRUbclgg"
      + "ZCWN9wS8nnFwDR8A1XLGtT9GQJ8vCr30SviXsh6qRlzEf47ZBTFqPhwnTLQxmWvCIXvJrIA8UFEZAb2nsJVhcmy6E"
      + "ZAxjvPVrDf9N9FX1fLBkaI5VK1ZBMU5";

  private final String invalidJwtToken = "EAALXaskmJ0ABAF8B3obWu";

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  AuthController authController;

  @Test
  void getUserTest() {
    FacebookUser facebookUser = facebookClient.getUser(jwtToken);
    assertThat(facebookUser.getId(), facebookUser.getId().equals("103318825907086"));
    assertThat(facebookUser.getEmail(), facebookUser.getEmail().equals("grata.salve@gmail.com"));
    assertThat(facebookUser.getFirst_name(), facebookUser.getFirst_name().equals("Влад"));
    assertThat(facebookUser.getLast_name(), facebookUser.getLast_name().equals("Прокопенко"));
  }

  @Test
  void getUserWithInvalidTokenTest() {
    assertThrows(InvalidJwtTokenException.class, () -> facebookClient.getUser(invalidJwtToken));
  }

  @Test
  void loginUserTest() {
    String token = facebookService.loginUser(jwtToken);
    assertThat(token, token.length() > 0);
  }

  @Mock
  JwtConfig jwtConfig;

  @Mock
  JwtTokenProvider tokenProvider;

  @InjectMocks
  JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

  HttpServletRequest request;
  HttpServletResponse response;
  FilterChain chain;

  @BeforeEach
  public void before() {
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    chain = mock(FilterChain.class);
    jwtConfig = mock(JwtConfig.class);
    tokenProvider = mock(JwtTokenProvider.class);
  }

  @Test
  public void doFilterInternalNullToken() throws IOException, ServletException {
    when(request.getHeader(AUTHORIZATION)).thenReturn(null);
    jwtTokenAuthenticationFilter.doFilterInternal(request, response, chain);
    verify(chain).doFilter(request, response);
  }

  @Test
  public void doFilterInternalExceptionToken() throws IOException, ServletException {
    when(request.getHeader(AUTHORIZATION)).thenReturn("123");
    jwtTokenAuthenticationFilter.doFilterInternal(request, response, chain);
    verify(chain).doFilter(request, response);
  }

  @Test
  public void validateExpiredTokenTest(CapturedOutput output) {
    jwtTokenProvider.validateToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJncmF0YS5zYWx2ZUBnb"
        + "WFpbC5jb20iLCJmaXJzdF9uYW1lIjoi0JLQu9Cw0LQiLCJsYXN0X25hbWUiOiLQn9GA0L7QutC-0L_Qt"
        + "dC90LrQviIsImF1dGhvcml0aWVzIjpbIlJPTEVfQ0FORElEQVRFIl0sImlhdCI6MTY3MDE4MDcyNywiZXh"
        + "wIjoxNjcwMjY3MTI3fQ.1jmzeuBWOGh40cS9QxukX8md2Uv4UC6cylOzpMS1rD4AgxJVNW4J-kbk6nyze8Y"
        + "mmYy1Dd64fqggBxkNsu8laQ");
    Assertions.assertTrue(output.getOut().contains("Expired JWT token"));
  }

  @Test
  public void validateMalformedTokenTest(CapturedOutput output) {
    jwtTokenProvider.validateToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJncmF0YS5zYWx2ZUBnb"
        + "WFpbC5jQiLCJsYXN0X25hbWUiOiLQn9GA0L7QutC-0L_Qt"
        + "dC90LrQviIsImF1dGhvcml0aWVzIjMS1rD4AgxJVNW4J-kbk6nyze8Y"
        + "mmYy1Dd64fqggBxkNsu8laQ");
    Assertions.assertTrue(output.getOut().contains("Invalid JWT token"));
  }

  @Test
  public void getClaimsFromJwtCorrectTest() {
    String info = String.valueOf(
        jwtTokenProvider.getClaimsFromJwt(facebookService.loginUser(jwtToken)));
    Assertions.assertTrue(info.contains("grata.salve@gmail.com"));
    Assertions.assertTrue(info.contains("Влад"));
    Assertions.assertTrue(info.contains("Прокопенко"));
  }

  @Test
  public void facebookAuthCorrectTest() {
    JwtAuthenticationResponse response = (JwtAuthenticationResponse)
        authController.facebookAuth(new FacebookLoginRequest(jwtToken)).getBody();
    String info = String.valueOf(
        jwtTokenProvider.getClaimsFromJwt(Objects.requireNonNull(response).getAccessToken()));
    Assertions.assertTrue(info.contains("grata.salve@gmail.com"));
    Assertions.assertTrue(info.contains("Влад"));
    Assertions.assertTrue(info.contains("Прокопенко"));
  }

  @Test
  public void facebookAuthEmptyResponseTest() {
    assertThrows(InvalidJwtTokenException.class, () ->
        authController.facebookAuth(new FacebookLoginRequest(null)).getBody());
  }

}