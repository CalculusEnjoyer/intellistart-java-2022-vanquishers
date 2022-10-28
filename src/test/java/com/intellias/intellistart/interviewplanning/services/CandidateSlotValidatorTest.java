package com.intellias.intellistart.interviewplanning.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.intellias.intellistart.interviewplanning.controllers.dto.CandidateSlotDto;
import com.intellias.intellistart.interviewplanning.models.CandidateSlot;
import com.intellias.intellistart.interviewplanning.util.validation.CandidateSlotValidator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
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
public class CandidateSlotValidatorTest {

  @Autowired
  private ModelMapper mapper;

  private static final int YEAR = LocalDate.now().getYear() + 1;
  private static final List<CandidateSlot> SLOTS = List.of(
      new CandidateSlot(
          // 13.05.2023
          // PASS
          LocalDateTime.of(LocalDate.of(YEAR, Month.MAY, 13), LocalTime.of(12, 0)),
          LocalDateTime.of(LocalDate.of(YEAR, Month.MAY, 13), LocalTime.of(14, 0))
      ),
      new CandidateSlot(
          // 15.05.2023
          // PASS
          LocalDateTime.of(LocalDate.of(YEAR, Month.MAY, 15), LocalTime.of(9, 30)),
          LocalDateTime.of(LocalDate.of(YEAR, Month.MAY, 15), LocalTime.of(11, 0))
      ),
      new CandidateSlot(
          // 12.05.2023
          // minutes are not rounded to 30
          // ERROR
          LocalDateTime.of(LocalDate.of(YEAR, Month.MAY, 12), LocalTime.of(8, 55)),
          LocalDateTime.of(LocalDate.of(YEAR, Month.MAY, 12), LocalTime.of(11, 0))
      ),
      new CandidateSlot(
          // 14.05.2023
          // duration lass than 90 min
          // ERROR
          LocalDateTime.of(LocalDate.of(YEAR, Month.MAY, 14), LocalTime.of(10, 0)),
          LocalDateTime.of(LocalDate.of(YEAR, Month.MAY, 14), LocalTime.of(11, 0))
      ),
      new CandidateSlot(
          // 14.05.2023
          // from-hour is larger than to-hour
          // ERROR
          LocalDateTime.of(LocalDate.of(YEAR, Month.MAY, 14), LocalTime.of(11, 0)),
          LocalDateTime.of(LocalDate.of(YEAR, Month.MAY, 14), LocalTime.of(10, 0))
      )
  );
  private static final int ERROR_COUNT = 3;
  private static final int FINAL_SIZE = 2;

  @Test
  @Order(1)
  public void testValidator() {

    int errors = 0;
    List<CandidateSlotDto> validatedSlots = new ArrayList<>();

    for (CandidateSlot slot: SLOTS) {
      try {
        CandidateSlotDto dto = mapper.map(slot, CandidateSlotDto.class);
        System.out.println(CandidateSlotValidator.validDtoOrError(dto));
        validatedSlots.add(CandidateSlotValidator.validDtoOrError(dto));
      } catch (Exception e) {
        System.out.println(e.getClass());
        errors++;
      }
    }

    assertThat(errors).isEqualTo(ERROR_COUNT);
    assertThat(validatedSlots.size()).isEqualTo(FINAL_SIZE);
  }
}
