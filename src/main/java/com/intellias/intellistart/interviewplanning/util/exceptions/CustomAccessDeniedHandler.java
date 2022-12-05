package com.intellias.intellistart.interviewplanning.util.exceptions;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * The user does not have access rights.
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(
      HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException exc) throws IOException {
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.getOutputStream().println("{\"errorCode\": \"not_authorized\",");
    response.getOutputStream().println("    \"errorMessage\": \"You are not authorized "
        + "to use this functionality\" }");
  }
}