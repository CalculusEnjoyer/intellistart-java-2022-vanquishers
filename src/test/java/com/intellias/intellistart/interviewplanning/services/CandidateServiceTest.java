package com.intellias.intellistart.interviewplanning.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.intellias.intellistart.interviewplanning.models.Candidate;
import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import com.intellias.intellistart.interviewplanning.models.User;
import com.intellias.intellistart.interviewplanning.models.enums.Role;
import com.intellias.intellistart.interviewplanning.util.exceptions.CandidateSlotNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
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
class CandidateServiceTest {

  @Autowired
  private CandidateService candidateService;

  @Autowired
  private UserService userService;

  @Autowired
  private ModelMapper mapper;

  @Autowired
  private WeekService weekService;

  private static final int NEXT_YEAR = LocalDate.now().getYear() + 1;

  private static final List<CandidateSlot> SLOTS = List.of(
      new CandidateSlot(
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, 10, 12), LocalTime.of(9, 30)),
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, 10, 12), LocalTime.of(11, 0))
      ),
      new CandidateSlot(
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, 10, 13), LocalTime.of(9, 30)),
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, 10, 13), LocalTime.of(11, 0))
      ),
      new CandidateSlot(
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, 10, 14), LocalTime.of(9, 30)),
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, 10, 14), LocalTime.of(11, 0))
      ),
      new CandidateSlot(
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, 10, 15), LocalTime.of(9, 30)),
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, 10, 15), LocalTime.of(11, 0))
      )
  );

  private static List<CandidateSlot> ADDED_SLOTS;

  @Test
  @Order(0)
  void registerValidUserAndCheck() {
    User user = new User("emailtest121@test.com", Role.CANDIDATE);
    int beforeUserSize = userService.findAllUsersByRole(Role.CANDIDATE).size();
    userService.registerUser(user);
    int afterUserSize = userService.findAllUsersByRole(Role.CANDIDATE).size();

    int beforeIntSize = candidateService.getAllCandidates().size();
    Candidate candidate = new Candidate(new HashSet<>(), user);
    candidateService.registerCandidate(candidate);
    int afterIntSize = candidateService.getAllCandidates().size();

    SLOTS.forEach(e -> e.setCandidate(candidate));
    assertThat(beforeUserSize + 1).isEqualTo(afterUserSize);
    assertThat(beforeIntSize + 1).isEqualTo(afterIntSize);
  }

  @Test
  @Order(1)
  void addCandidateSlotsTest() {
    int initSize = candidateService.getAllSlots().size();

    ADDED_SLOTS = new ArrayList<>();
    SLOTS.forEach(slot -> {
      try {
        candidateService.registerSlot(slot);
      } catch (Exception ex) {
        return;
      }

      ADDED_SLOTS.add(slot);
    });

    int expectedDbTableSize = initSize + SLOTS.size();
    int actualDbTableSize = candidateService.getAllSlots().size();

    assertThat(ADDED_SLOTS).hasSameSizeAs(SLOTS);
    assertThat(actualDbTableSize).isEqualTo(expectedDbTableSize);
  }

  @Test
  @Order(2)
  void readInterviewerSlotsTest() {
    List<Long> addedSlotIds = ADDED_SLOTS.stream()
        .map(CandidateSlot::getId)
        .collect(Collectors.toList());

    List<CandidateSlot> readSlots = candidateService.getAllSlots().stream()
        .filter(slot -> addedSlotIds.contains(slot.getId()))
        .map(slot -> mapper.map(slot, CandidateSlot.class))
        .collect(Collectors.toList());

    assertThat(readSlots).hasSameElementsAs(ADDED_SLOTS);
  }

  @Test
  @Order(3)
  void deleteCandidateSlotsTest() {
    int beforeDeleteSize = candidateService.getAllSlots().size();

    ADDED_SLOTS.forEach(slot -> {
      candidateService.deleteSlotById(slot.getId());
      assertThrows(CandidateSlotNotFoundException.class,
          () -> candidateService.getSlotById(slot.getId()));
    });
    int afterDeleteSize = candidateService.getAllSlots().size();
    int expectedSize = beforeDeleteSize - ADDED_SLOTS.size();

    assertThat(afterDeleteSize).isEqualTo(expectedSize);
  }

  @Test
  @Order(4)
  void findCandidateByUserId() {
    User newUser = new User("check@gmail.com", Role.CANDIDATE);
    userService.registerUser(newUser);
    Candidate newCandidate = new Candidate();
    newCandidate.setUser(newUser);
    candidateService.registerCandidate(newCandidate);

    Assertions.assertEquals(newCandidate.getId(),
        candidateService.getCandidateByUserId(newUser.getId()).getId());
  }

  @Test
  @Order(5)
  void findCandidateSlotByWeekTest() {
    CandidateSlot candidateSlotToFind = new CandidateSlot(
        LocalDateTime.of(LocalDate.of(NEXT_YEAR, Month.DECEMBER, 12), LocalTime.of(9, 30)),
        LocalDateTime.of(LocalDate.of(NEXT_YEAR, Month.DECEMBER, 12), LocalTime.of(18, 0)));
    Candidate candidate = new Candidate();
    candidateService.registerCandidate(candidate);
    candidateSlotToFind.setCandidate(candidate);
    candidateService.registerSlot(candidateSlotToFind);

    List<CandidateSlot> slots = candidateService.getCandidateSlotsForWeek(
        weekService.getWeekNumFrom(LocalDate.of(NEXT_YEAR, Month.DECEMBER, 12)));
    List<CandidateSlot> filteredSlots = slots.stream()
        .filter(slot -> Objects.equals(slot.getDateFrom(),
            LocalDateTime.of(LocalDate.of(NEXT_YEAR, Month.DECEMBER, 12), LocalTime.of(9, 30)))).collect(
            Collectors.toList());

    Assertions.assertEquals(
        LocalDateTime.of(LocalDate.of(NEXT_YEAR, Month.DECEMBER, 12), LocalTime.of(9, 30)),
        filteredSlots.get(0).getDateFrom());
  }
}
