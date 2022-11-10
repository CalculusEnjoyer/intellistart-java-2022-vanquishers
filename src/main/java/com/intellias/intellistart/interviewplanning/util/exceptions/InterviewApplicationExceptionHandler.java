package com.intellias.intellistart.interviewplanning.util.exceptions;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception handler.
 */
@ControllerAdvice
public class InterviewApplicationExceptionHandler {

  @ExceptionHandler(InterviewApplicationException.class)
  public ResponseEntity<Object> exception(InterviewApplicationException e) {
    return ResponseEntity.status(e.getHttpStatus())
        .body(Map.of("errorCode", e.getErrorCode(), "errorMessage", e.getErrorMessage()));
  }

  /**
   * Exception handler for MethodArgumentNotValidException that raises when
   * {@code javax.validation.constraints} annotations are used for provided with request DTOs and
   * corresponding checks are failing.
   *
   * @param ex MethodArgumentNotValidException exception
   * @return map with accumulated in {@code errorMessage} key errors
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Map.of(
            "errorCode", "bad_request",
            "errorMessage", "Invalid fields: " + errors));
  }
}
