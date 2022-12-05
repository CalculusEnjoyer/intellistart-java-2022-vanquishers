package com.intellias.intellistart.interviewplanning.util.exceptions;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * User not authorized exception.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  /**
   * Method to get exception info for unauthorized users.
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException {
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.getOutputStream().println("{\"errorCode\": \"not_authorized\",");
    response.getOutputStream().println("    \"errorMessage\": \"You are not authorized "
        + "to use this functionality\" }");
  }
}