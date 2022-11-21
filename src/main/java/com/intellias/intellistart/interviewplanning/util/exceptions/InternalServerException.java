package com.intellias.intellistart.interviewplanning.util.exceptions;


import org.springframework.http.HttpStatus;

/**
 * Internal server error exception.
 */
public class InternalServerException extends InterviewApplicationException {

  public InternalServerException(String message) {
    super("internal_server_exception", HttpStatus.INTERNAL_SERVER_ERROR, message);
  }

}
