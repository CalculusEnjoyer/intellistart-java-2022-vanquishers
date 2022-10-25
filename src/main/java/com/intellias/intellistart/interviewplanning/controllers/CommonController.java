package com.intellias.intellistart.interviewplanning.controllers;


import com.intellias.intellistart.interviewplanning.util.WeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Common controller. Manages the common, 'any user' endpoints for getting the basic information
 * like current or next week, or info about current user
 */

@RestController
public class CommonController {


  private final WeekService weekService;

  @Autowired
  public CommonController(WeekService weekService) {
    this.weekService = weekService;
  }

  /**
   * Endpoint for getting the information about the current user.
   *
   * @return response status
   */
  @GetMapping("/me")
  public ResponseEntity<HttpStatus> getMe() {
    return ResponseEntity.ok(HttpStatus.OK);
  }

  /**
   * Endpoint for getting the information about the current week number.
   *
   * @return response status
   */
  @GetMapping("/weeks/current")
  public int getCurrentWeek() {
    return weekService.getCurrentWeekNum();
  }

  /**
   * Endpoint for getting the information about the next week number.
   *
   * @return response status
   */
  @GetMapping("/weeks/next")
  public int getNextWeek() {
    return weekService.getNextWeekNum();
  }

}
