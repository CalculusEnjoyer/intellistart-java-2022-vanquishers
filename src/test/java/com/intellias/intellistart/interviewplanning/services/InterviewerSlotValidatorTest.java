package com.intellias.intellistart.interviewplanning.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
  InterviewerService interviewerService;

  @Test
  @Order(1)
  void validateOverLappingOfSlotsTest() {
    Interviewer interviewer = new Interviewer();
    InterviewerSlot interviewerSlot = new InterviewerSlot(202243, 1,
        LocalTime.of(9, 30), LocalTime.of(11, 0));
    InterviewerSlot interviewerSlotThatOver = new InterviewerSlot(202243, 1,
        LocalTime.of(10, 30), LocalTime.of(12, 0));
    InterviewerSlot interviewerSlotThatOver1 = new InterviewerSlot(202243, 1,
        LocalTime.of(9, 0), LocalTime.of(10, 30));
    InterviewerSlot interviewerSlotThatNotOverlaps1 = new InterviewerSlot(202244, 1,
        LocalTime.of(10, 30), LocalTime.of(12, 0));
    InterviewerSlot interviewerSlotThatNotOverlaps2 = new InterviewerSlot(202244, 1,
        LocalTime.of(16, 30), LocalTime.of(18, 0));

    interviewer.setInterviewerSlot(Set.of(interviewerSlot));
    interviewerService.registerInterviewer(interviewer);

    assertThrows(OverlappingSlotException.class,
        () -> InterviewerValidator.validateOverLappingOfSlots(interviewer,
            interviewerSlotThatOver));
    assertThrows(OverlappingSlotException.class,
        () -> InterviewerValidator.validateOverLappingOfSlots(interviewer,
            interviewerSlotThatOver1));
    assertDoesNotThrow(() -> InterviewerValidator.validateOverLappingOfSlots(interviewer,
        interviewerSlotThatNotOverlaps1));
    assertDoesNotThrow(() -> InterviewerValidator.validateOverLappingOfSlots(interviewer,
        interviewerSlotThatNotOverlaps2));
  }
}
