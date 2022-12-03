package com.intellias.intellistart.interviewplanning.auth;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.intellias.intellistart.interviewplanning.config.JwtConfig;
import com.intellias.intellistart.interviewplanning.config.JwtTokenAuthenticationFilter;
import com.intellias.intellistart.interviewplanning.models.security.FacebookUser;
import com.intellias.intellistart.interviewplanning.services.FacebookClient;
import com.intellias.intellistart.interviewplanning.services.FacebookService;
import com.intellias.intellistart.interviewplanning.services.JwtTokenProvider;
import com.intellias.intellistart.interviewplanning.services.UserService;
import com.intellias.intellistart.interviewplanning.util.exceptions.InvalidJwtTokenException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class AuthenticationTest {
  @Autowired
  private FacebookClient facebookClient;

  @Autowired
  private UserService userService;

  @Autowired
  FacebookService facebookService;

  private final String jwtToken = "EAALXaskmJ0ABALd1Ig5KUIKpZCor5UjnwmgHj1R08J4qprqoV2rFlfvRUbclgg"
      + "ZCWN9wS8nnFwDR8A1XLGtT9GQJ8vCr30SviXsh6qRlzEf47ZBTFqPhwnTLQxmWvCIXvJrIA8UFEZAb2nsJVhcmy6E"
      + "ZAxjvPVrDf9N9FX1fLBkaI5VK1ZBMU5";

  private final String invalidJwtToken = "EAALXaskmJ0ABAF8B3obWu";

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

}