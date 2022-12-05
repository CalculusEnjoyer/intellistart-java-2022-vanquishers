package com.intellias.intellistart.interviewplanning.util.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Invalid Jwt Token exception.
 */
public class InvalidJwtTokenException extends InterviewApplicationException {

  /**
   * Invalid Jwt Token Exception basic constructor.
   *
   * @param message describe problem occurred with JWT Token.
   */
  public InvalidJwtTokenException(String message) {
    super("invalid_jwt_token_exception", HttpStatus.UNAUTHORIZED, message);
  }
}
