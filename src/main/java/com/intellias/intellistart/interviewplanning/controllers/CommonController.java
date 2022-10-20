package com.intellias.intellistart.interviewplanning.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Common controller.
 * Manages the common, 'any user' endpoints for getting the basic information
 * like current or next week, or info about current user
 */

@RestController
public class CommonController {

  /**
   * Endpoint for getting the information about the current user.

   * @return response status
   */
  @GetMapping("/me")
  public ResponseEntity<HttpStatus> getMe() {
    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Endpoint for getting the information about the current week number.

   * @return response status
   */
  @GetMapping("/weeks/current")
  public ResponseEntity<HttpStatus> getCurrentWeek() {
    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Endpoint for getting the information about the next week number.

   * @return response status
   */
  @GetMapping("/weeks/next")
  public ResponseEntity<HttpStatus> getNextWeek() {
    return ResponseEntity.ok(HttpStatus.OK);
  }

}
