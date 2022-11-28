package com.intellias.intellistart.interviewplanning.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.intellias.intellistart.interviewplanning.controllers.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.models.Interviewer;
import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.models.User;
import com.intellias.intellistart.interviewplanning.models.enums.Role;
import com.intellias.intellistart.interviewplanning.util.exceptions.InterviewerNotFoundException;
import com.intellias.intellistart.interviewplanning.util.exceptions.InterviewerSlotNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class InterviewerServiceTest {

  @Autowired
  private InterviewerService interviewerService;

  @Autowired
  private UserService userService;

  @Autowired
  private ModelMapper mapper;

  private static final List<InterviewerSlot> SLOTS = List.of(
      new InterviewerSlot(0, 1,
          LocalTime.of(9, 30), LocalTime.of(11, 0)),

      new InterviewerSlot(2, 2,
          LocalTime.of(9, 30), LocalTime.of(11, 0)),

      new InterviewerSlot(1, 3,
          LocalTime.of(9, 30), LocalTime.of(11, 0)),

      new InterviewerSlot(1, 4,
          LocalTime.of(9, 30), LocalTime.of(11, 0))
  );

  private static final List<InterviewerSlot> ADDED_SLOTS = new ArrayList<>();

  @Test
  @Order(0)
  void registerValidUserAndCheck() {
    User user = new User("testemail12@test.com", Role.INTERVIEWER);
    int beforeUserSize = userService.findAllUsersByRole(Role.INTERVIEWER).size();
    userService.registerUser(user);
    int afterUserSize = userService.findAllUsersByRole(Role.INTERVIEWER).size();

    int beforeIntSize = interviewerService.getAllInterviewers().size();
    Interviewer interviewer = new Interviewer(user, 100, new HashSet<>());
    interviewerService.registerInterviewer(interviewer);
    int afterIntSize = interviewerService.getAllInterviewers().size();

    SLOTS.forEach(e -> e.setInterviewer(interviewer));
    assertThat(beforeUserSize + 1).isEqualTo(afterUserSize);
    assertThat(beforeIntSize + 1).isEqualTo(afterIntSize);
  }

  @Test
  @Order(1)
  void addInterviewerSlotsTest() {
    int initSize = interviewerService.getAllSlots().size();

    SLOTS.forEach(e -> ADDED_SLOTS.add(interviewerService.registerSlot(e)));
    int expectedDbTableSize = initSize + SLOTS.size();
    int actualDbTableSize = interviewerService.getAllSlots().size();

    assertThat(ADDED_SLOTS).hasSameSizeAs(SLOTS);
    assertThat(actualDbTableSize).isEqualTo(expectedDbTableSize);
  }

  @Test
  @Order(2)
  void readInterviewerSlotsTest() {
    List<Long> addedSlotIds = ADDED_SLOTS.stream()
        .map(InterviewerSlot::getId)
        .collect(Collectors.toList());

    List<InterviewerSlot> readSlots = interviewerService.getAllSlots().stream()
        .filter(slot -> addedSlotIds.contains(slot.getId()))
        .collect(Collectors.toList());

    assertThat(readSlots).hasSameElementsAs(ADDED_SLOTS);
  }

  @Test
  @Order(3)
  void deleteInterviewerSlotsTest() {

    int BeforeDeleteSize = interviewerService.getAllSlots().size();

    ADDED_SLOTS.forEach(slot -> {
      interviewerService.deleteSlotById(slot.getId());
      assertThrows(InterviewerSlotNotFoundException.class,
          () -> interviewerService.getSlotById(slot.getId()));
    });
    int afterDeleteSize = interviewerService.getAllSlots().size();
    int expectedSize = BeforeDeleteSize - ADDED_SLOTS.size();

    assertThat(afterDeleteSize).isEqualTo(expectedSize);
  }

  @Test
  @Order(4)
  void findInterviewerByIdThrowExceptionTest() {
    assertThatThrownBy(() -> interviewerService.getInterviewerById(-1L))
        .isInstanceOf(InterviewerNotFoundException.class);
  }

  @Test
  @Order(5)
  void registerAndGetInterviewerTest() {
    int countBeforeRegister = interviewerService.getAllInterviewers().size();
    int expectedBookingLimit = 10;
    Interviewer interviewer = new Interviewer();
    interviewer.setBookingLimit(expectedBookingLimit);

    Interviewer interviewerRegistered =
        interviewerService.registerInterviewer(interviewer);
    Interviewer interviewerGot =
        interviewerService.getInterviewerById(interviewerRegistered.getId());
    int countAfterRegister = interviewerService.getAllInterviewers().size();

    assertThat(countBeforeRegister).isEqualTo(countAfterRegister - 1);
    assertThat(interviewerRegistered.getBookingLimit()).isEqualTo(expectedBookingLimit);
    assertThat(interviewerGot.getBookingLimit()).isEqualTo(expectedBookingLimit);
  }

  @Test
  @Order(6)
  void getSlotsForIdAndWeekTest() {
    long interviewerId = interviewerService.registerInterviewer(new Interviewer()).getId();

    InterviewerSlotDto testSlotDto = new InterviewerSlotDto(202243, 1,
        LocalTime.of(9, 30), LocalTime.of(11, 0), interviewerId);
    InterviewerSlot testSlot = mapper.map(testSlotDto, InterviewerSlot.class);
    InterviewerSlot registeredSlot = interviewerService.registerSlot(testSlot);

    List<InterviewerSlot> wrongWeekSlots =
        interviewerService.getSlotsForIdAndWeek(interviewerId, 0);
    List<InterviewerSlot> wrongIdSlots =
        interviewerService.getSlotsForIdAndWeek(0L, 202243);
    List<InterviewerSlot> correctSlots =
        interviewerService.getSlotsForIdAndWeek(interviewerId, 202243);

    System.out.println(correctSlots);
    interviewerService.deleteSlotById(registeredSlot.getId());

    assertThat(wrongWeekSlots).isEmpty();
    assertThat(wrongIdSlots).isEmpty();
    assertThat(correctSlots).contains(registeredSlot);
  }
}
