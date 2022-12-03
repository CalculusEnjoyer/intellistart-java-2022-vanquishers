package com.intellias.intellistart.interviewplanning.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.intellias.intellistart.interviewplanning.controllers.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.models.Interviewer;
import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.util.exceptions.OverlappingSlotException;
import com.intellias.intellistart.interviewplanning.util.validation.InterviewerValidator;
import java.time.LocalTime;
import java.util.Set;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InterviewerSlotValidatorTest {

  @Autowired
  private InterviewerService interviewerService;

  @Autowired
  private InterviewerValidator interviewerValidator;

  @Test
  @Order(1)
  void validateOverLappingOfSlotsTest() {
    Interviewer interviewer = new Interviewer();
    InterviewerSlot interviewerSlot = new InterviewerSlot(202243, 1,
        LocalTime.of(9, 30), LocalTime.of(11, 0));
    InterviewerSlotDto interviewerSlotThatOver = new InterviewerSlotDto(202243, 1,
        LocalTime.of(10, 30), LocalTime.of(12, 0), 1L);
    InterviewerSlotDto interviewerSlotThatOver1 = new InterviewerSlotDto(202243, 1,
        LocalTime.of(9, 0), LocalTime.of(10, 30), 1L);
    InterviewerSlotDto interviewerSlotThatNotOverlaps1 = new InterviewerSlotDto(202244, 1,
        LocalTime.of(10, 30), LocalTime.of(12, 0), 1L);
    InterviewerSlotDto interviewerSlotThatNotOverlaps2 = new InterviewerSlotDto(202244, 1,
        LocalTime.of(16, 30), LocalTime.of(18, 0), 1L);

    interviewer.setInterviewerSlot(Set.of(interviewerSlot));
    interviewerService.registerInterviewer(interviewer);

    assertThrows(OverlappingSlotException.class,
        () -> interviewerValidator.validateOverLappingOfSlots(interviewer.getInterviewerSlot(),
            interviewerSlotThatOver));
    assertThrows(OverlappingSlotException.class,
        () -> interviewerValidator.validateOverLappingOfSlots(interviewer.getInterviewerSlot(),
            interviewerSlotThatOver1));
    assertDoesNotThrow(
        () -> interviewerValidator.validateOverLappingOfSlots(interviewer.getInterviewerSlot(),
            interviewerSlotThatNotOverlaps1));
    assertDoesNotThrow(
        () -> interviewerValidator.validateOverLappingOfSlots(interviewer.getInterviewerSlot(),
            interviewerSlotThatNotOverlaps2));
  }
}
