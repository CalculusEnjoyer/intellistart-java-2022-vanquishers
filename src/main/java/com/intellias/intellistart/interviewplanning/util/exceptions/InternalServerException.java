package com.intellias.intellistart.interviewplanning.util.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Internal server error exception.
 */
public class InternalServerException extends InterviewApplicationException {

  public InternalServerException(String message) {
    super("internal_server_exception", HttpStatus.INTERNAL_SERVER_ERROR, message);
  }

}
