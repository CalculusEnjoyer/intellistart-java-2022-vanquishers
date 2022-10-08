package com.intellias.intellistart.interviewplanning.controllers;

import com.intellias.intellistart.interviewplanning.models.Role;
import com.intellias.intellistart.interviewplanning.models.User;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Candidate test controller.
 */
@RestController
@RequestMapping(CandidateController.MAPPING)
public class CandidateController {

  public static final String MAPPING = "/candidate";

  /**
   * Method for test request generating time slots.

   * @return response status
   */
  @GetMapping("/addTimeSlots")
  public ResponseEntity<HttpStatus> addTimeSlots() {

    return ResponseEntity.ok(HttpStatus.OK);
  }

}
