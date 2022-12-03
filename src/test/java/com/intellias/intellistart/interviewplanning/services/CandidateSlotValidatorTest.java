package com.intellias.intellistart.interviewplanning.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.intellias.intellistart.interviewplanning.controllers.dto.CandidateSlotDto;
import com.intellias.intellistart.interviewplanning.models.Candidate;
import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import com.intellias.intellistart.interviewplanning.util.exceptions.OverlappingSlotException;
import com.intellias.intellistart.interviewplanning.util.validation.CandidateValidator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
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
class CandidateSlotValidatorTest {

  @Autowired
  private CandidateService candidateService;

  @Autowired
  private CandidateValidator candidateValidator;


  private static final int NEXT_YEAR = LocalDate.now().getYear() + 1;
  private static final List<CandidateSlot> SLOTS = List.of(
      new CandidateSlot(
          // 13.05.2023
          // PASS
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, Month.MAY, 13), LocalTime.of(12, 0)),
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, Month.MAY, 13), LocalTime.of(14, 0))
      ),
      new CandidateSlot(
          // 15.05.2023
          // PASS
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, Month.MAY, 15), LocalTime.of(9, 30)),
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, Month.MAY, 15), LocalTime.of(11, 0))
      ),
      new CandidateSlot(
          // 12.05.2023
          // minutes are not rounded to 30
          // ERROR
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, Month.MAY, 12), LocalTime.of(8, 55)),
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, Month.MAY, 12), LocalTime.of(11, 0))
      ),
      new CandidateSlot(
          // 14.05.2023
          // duration lass than 90 min
          // ERROR
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, Month.MAY, 14), LocalTime.of(10, 0)),
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, Month.MAY, 14), LocalTime.of(11, 0))
      ),
      new CandidateSlot(
          // 14.05.2023
          // from-hour is larger than to-hour
          // ERROR
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, Month.MAY, 14), LocalTime.of(11, 0)),
          LocalDateTime.of(LocalDate.of(NEXT_YEAR, Month.MAY, 14), LocalTime.of(10, 0))
      )
  );
  private static final int ERROR_COUNT = 3;
  private static final int FINAL_SIZE = 2;

  @Test
  @Order(1)
  void testValidator() {

    int errors = 0;
    int validatedSlots = 0;

    for (CandidateSlot slot : SLOTS) {
      try {
        candidateValidator.validateCandidateSlotForBoundaries(slot);
        candidateValidator.validateCandidateSlotForOverlapping(
            new HashSet<>(candidateService.getAllSlots()), slot);
        validatedSlots++;
      } catch (Exception e) {
        errors++;
      }
    }

    assertThat(errors).isEqualTo(ERROR_COUNT);
    assertThat(validatedSlots).isEqualTo(FINAL_SIZE);
  }

  @Test
  @Order(2)
  void validateCandidateSlotForOverlapping() {
    CandidateSlotDto overlapping = new CandidateSlotDto(
        LocalDateTime.of(LocalDate.of(NEXT_YEAR, Month.MAY, 13), LocalTime.of(13, 0)),
        LocalDateTime.of(LocalDate.of(NEXT_YEAR, Month.MAY, 13), LocalTime.of(15, 0)), 1L
    );

    CandidateSlotDto overlapping1 = new CandidateSlotDto(
        LocalDateTime.of(LocalDate.of(NEXT_YEAR, Month.MAY, 15), LocalTime.of(10, 0)),
        LocalDateTime.of(LocalDate.of(NEXT_YEAR, Month.MAY, 15), LocalTime.of(14, 0)), 1L
    );

    CandidateSlotDto notOverlapping = new CandidateSlotDto(
        LocalDateTime.of(LocalDate.of(NEXT_YEAR, Month.MAY, 13), LocalTime.of(14, 0)),
        LocalDateTime.of(LocalDate.of(NEXT_YEAR, Month.MAY, 13), LocalTime.of(15, 0)), 1L
    );

    CandidateSlotDto notOverlapping1 = new CandidateSlotDto(
        LocalDateTime.of(LocalDate.of(NEXT_YEAR, Month.MAY, 26), LocalTime.of(12, 0)),
        LocalDateTime.of(LocalDate.of(NEXT_YEAR, Month.MAY, 26), LocalTime.of(14, 0)), 1L
    );

    Candidate candidate = new Candidate();
    candidate.setCandidateSlot(new HashSet<>(SLOTS));

    assertThrows(OverlappingSlotException.class,
        () -> candidateValidator.validateCandidateSlotForOverlapping(
            candidate.getCandidateSlot(),
            overlapping));
    assertThrows(OverlappingSlotException.class,
        () -> candidateValidator.validateCandidateSlotForOverlapping(
            candidate.getCandidateSlot(),
            overlapping1));
    assertDoesNotThrow(
        () -> candidateValidator.validateCandidateSlotForOverlapping(
            candidate.getCandidateSlot(),
            notOverlapping));
    assertDoesNotThrow(
        () -> candidateValidator.validateCandidateSlotForOverlapping(
            candidate.getCandidateSlot(),
            notOverlapping1));
  }
}
