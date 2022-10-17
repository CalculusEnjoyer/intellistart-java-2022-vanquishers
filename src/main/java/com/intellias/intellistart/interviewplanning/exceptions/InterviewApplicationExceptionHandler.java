package com.intellias.intellistart.interviewplanning.exceptions;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception handler.
 */
@ControllerAdvice
public class InterviewApplicationExceptionHandler {

  @ExceptionHandler
  public ResponseEntity<Object> exception(InterviewApplicationException e) {
    return ResponseEntity.status(e.getHttpStatus())
        .body(Map.of("errorCode", e.getErrorCode(), "errorMessage", e.getErrorMessage()));
  }
}
