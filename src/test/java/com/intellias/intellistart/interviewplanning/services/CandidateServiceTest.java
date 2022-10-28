package com.intellias.intellistart.interviewplanning.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import com.intellias.intellistart.interviewplanning.util.exceptions.CandidateSlotNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
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
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CandidateServiceTest {

  @Autowired
  private CandidateService candidateService;

  @Autowired
  private ModelMapper mapper;

  private static final int YEAR = LocalDate.now().getYear();
  private static final List<CandidateSlot> slots = List.of(
      new CandidateSlot(
          LocalDateTime.of(LocalDate.of(YEAR, Month.OCTOBER, 12), LocalTime.of(9, 30)),
          LocalDateTime.of(LocalDate.of(YEAR, Month.OCTOBER, 12), LocalTime.of(11, 0))
      ),
      new CandidateSlot(
          LocalDateTime.of(LocalDate.of(YEAR, Month.OCTOBER, 13), LocalTime.of(9, 30)),
          LocalDateTime.of(LocalDate.of(YEAR, Month.OCTOBER, 13), LocalTime.of(11, 0))
      ),
      new CandidateSlot(
          LocalDateTime.of(LocalDate.of(YEAR, Month.OCTOBER, 14), LocalTime.of(9, 30)),
          LocalDateTime.of(LocalDate.of(YEAR, Month.OCTOBER, 14), LocalTime.of(11, 0))
      ),
      new CandidateSlot(
          LocalDateTime.of(LocalDate.of(YEAR, Month.OCTOBER, 15), LocalTime.of(9, 30)),
          LocalDateTime.of(LocalDate.of(YEAR, Month.OCTOBER, 15), LocalTime.of(11, 0))
      )
  );
  private static List<CandidateSlot> addedSlots;

  @Test
  @Order(1)
  void addCandidateSlotsTest() {
    int initSize = candidateService.getAllSlots().size();

    addedSlots = candidateService.registerSlots(slots);
    int expectedDbTableSize = initSize + slots.size();
    int actualDbTableSize = candidateService.getAllSlots().size();

    assertThat(addedSlots).hasSameSizeAs(slots);
    assertThat(actualDbTableSize).isEqualTo(expectedDbTableSize);
  }

  @Test
  @Order(2)
  void readInterviewerSlotsTest() {
    List<Long> addedSlotIds = addedSlots.stream()
        .map(CandidateSlot::getId)
        .collect(Collectors.toList());

    List<CandidateSlot> readSlots = candidateService.getAllSlots().stream()
        .filter(slot -> addedSlotIds.contains(slot.getId()))
        .map(slot -> mapper.map(slot, CandidateSlot.class))
        .collect(Collectors.toList());

    assertThat(readSlots).hasSameElementsAs(addedSlots);
  }

  @Test
  @Order(3)
  void deleteInterviewerSlotsTest() {
    int BeforeDeleteSize = candidateService.getAllSlots().size();
    List<CandidateSlot> deletedSlots = new ArrayList<>();

    addedSlots.forEach(slot -> {
      candidateService.deleteSlot(slot.getId());
      assertThrows(CandidateSlotNotFoundException.class,
          () -> candidateService.getSlotById(slot.getId()));
    });
    int afterDeleteSize = candidateService.getAllSlots().size();
    int expectedSize = BeforeDeleteSize - addedSlots.size();

    assertThat(afterDeleteSize).isEqualTo(expectedSize);
  }

}
