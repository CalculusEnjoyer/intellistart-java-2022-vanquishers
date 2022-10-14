package com.intellias.intellistart.interviewplanning.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.intellias.intellistart.interviewplanning.models.InterviewerSlot;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InterviewerServiceTest {
  @Autowired
  private InterviewerService interviewerService;

  @Autowired
  private ModelMapper mapper;

  private static final List<InterviewerSlot> slots = List.of(
      new InterviewerSlot(0, 1,
          LocalTime.of(9, 30), LocalTime.of(11, 0)),

      new InterviewerSlot(2, 2,
          LocalTime.of(9, 30), LocalTime.of(11, 0)),

      new InterviewerSlot(1, 3,
          LocalTime.of(9, 30), LocalTime.of(11, 0)),

      new InterviewerSlot(1, 4,
          LocalTime.of(9, 30), LocalTime.of(11, 0))
  );

  private static List<InterviewerSlot> addedSlots;

  @Test
  @Order(1)
  void addInterviewerSlotsTest() {
    int initSize = interviewerService.findAll().size();

    addedSlots = interviewerService.registerAll(slots);
    int expectedDbTableSize = initSize + slots.size();
    int actualDbTableSize = interviewerService.findAll().size();

    assertThat(addedSlots).hasSameSizeAs(slots);
    assertThat(actualDbTableSize).isEqualTo(expectedDbTableSize);
  }

  @Test
  @Order(2)
  void readInterviewerSlotsTest() {
    List<Long> addedSlotIds = addedSlots.stream()
        .map(InterviewerSlot::getId)
        .collect(Collectors.toList());

    List<InterviewerSlot> readSlots = interviewerService.findAll().stream()
        .filter(slot -> addedSlotIds.contains(slot.getId()))
        .map(slot -> mapper.map(slot, InterviewerSlot.class))
        .collect(Collectors.toList());

    assertThat(readSlots).hasSameElementsAs(addedSlots);
  }

  @Test
  @Order(3)
  void deleteInterviewerSlotsTest() {
    int BeforeDeleteSize = interviewerService.findAll().size();
    List<InterviewerSlot> deletedSlots = new ArrayList<>();

    addedSlots.forEach(slot -> {
      interviewerService.deleteSlot(slot.getId());
      interviewerService.findById(slot.getId()).ifPresent(deletedSlots::add);
    });
    int afterDeleteSize = interviewerService.findAll().size();
    int expectedSize = BeforeDeleteSize - addedSlots.size();

    assertThat(afterDeleteSize).isEqualTo(expectedSize);
    assertThat(deletedSlots).isEmpty();
  }
}
