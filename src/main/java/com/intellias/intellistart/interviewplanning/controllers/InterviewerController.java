package com.intellias.intellistart.interviewplanning.controllers;

import com.intellias.intellistart.interviewplanning.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.models.enums.Status;
import com.intellias.intellistart.interviewplanning.services.InterviewerService;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Interviewer controller.
 */
@RestController
@RequestMapping(InterviewerController.MAPPING)
public class InterviewerController {

  public static final String MAPPING = "/interviewers";

  public final InterviewerService interviewerService;

  @Autowired
  public InterviewerController(InterviewerService interviewerService) {
    this.interviewerService = interviewerService;
  }

  /**
   * Test generating of interviewer time slots.
   * /interviewers/addInterviewerSlots

   * @return string status
   */
  @GetMapping("/addInterviewerSlots")
  public String addInterviewerSlots() {

    List<InterviewerSlotDto> dtos = new ArrayList<>(
        Arrays.asList(
            new InterviewerSlotDto(0, DayOfWeek.MONDAY,
                LocalTime.of(9, 30), LocalTime.of(11, 0), Status.NEW),

            new InterviewerSlotDto(2, DayOfWeek.TUESDAY,
                LocalTime.of(9, 30), LocalTime.of(11, 0), Status.NEW),

            new InterviewerSlotDto(1, DayOfWeek.WEDNESDAY,
                LocalTime.of(9, 30), LocalTime.of(11, 0), Status.NEW),

            new InterviewerSlotDto(1, DayOfWeek.THURSDAY,
                LocalTime.of(9, 30), LocalTime.of(11, 0), Status.NEW)
        )
    );
    interviewerService.registerAll(dtos);

    return "OK";
  }

  /**
   * Test getting of interviewer time slots.
   * /interviewers/getInterviewerSlots

   * @return list of interviewer slots in DB
   */
  @GetMapping("/getInterviewerSlots")
  public List<InterviewerSlotDto> getInterviewerSlots() {
    return interviewerService.findAll();
  }

  /**
   * Test deleting of interviewer time slots.
   * /interviewers/delInterviewerSlots

   * @return list of interviewer slots in DB
   */
  @GetMapping("/delInterviewerSlots")
  public List<InterviewerSlotDto> deleteInterviewerSlots() {
    interviewerService.deleteAll();
    return interviewerService.findAll();
  }

}
