package com.intellias.intellistart.interviewplanning.util.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Same role exception.
 */
public class SameRoleChangeException extends InterviewApplicationException {

  /**
   * Overlapping slot exception constructor.
   */
  public SameRoleChangeException() {
    super("same_role",
        HttpStatus.BAD_REQUEST,
        "Can not revoke or grand to the role that has been already set.");
  }
}
