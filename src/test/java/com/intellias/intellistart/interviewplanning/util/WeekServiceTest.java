package com.intellias.intellistart.interviewplanning.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WeekServiceTest {

  @Autowired
  WeekService weekService;

  @Test
  void getNextWeekNum() {
    int nextWeek = weekService.getCurrentWeekNumInYear() + 1;
    int currentYear = LocalDate.now().getYear();

    assertThat(String.valueOf(currentYear) + nextWeek)
        .isEqualTo(String.valueOf(weekService.getNextWeekNumInFormat()));
  }
}
