package com.intellias.intellistart.interviewplanning.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WeekServiceTest {

  @Test
  void getSameWeekNumsForSameDateTest() {
    LocalDate date = LocalDate.now(WeekService.getZoneId());
    int expectedWeekNum = WeekService.getWeekNumFrom(date);
    int actualWeekNum = WeekService.getWeekNumFrom(date);

    assertThat(actualWeekNum).isEqualTo(expectedWeekNum);
  }

  @Test
  void getCurrentWeekNumTest() {
    int actualWeekNum = WeekService.getCurrentWeekNum();
    int expectedWeekNum = WeekService.getWeekNumFrom(LocalDate.now(WeekService.getZoneId()));

    // For avoiding the test failure on rare cases when the test
    // is performed on midnight of day, so two weekNums got different values
    // because got calculated in different days
    if (actualWeekNum != expectedWeekNum) {
      actualWeekNum = WeekService.getCurrentWeekNum();
      expectedWeekNum = WeekService.getWeekNumFrom(LocalDate.now(WeekService.getZoneId()));
    }

    assertThat(actualWeekNum).isEqualTo(expectedWeekNum);
  }

  @Test
  void getNextWeekNumTest() {
    int actualWeekNum = WeekService.getNextWeekNum();
    int expectedWeekNum = WeekService.getWeekNumFrom(
        LocalDate.now(WeekService.getZoneId()).plusDays(7));

    // For avoiding the test failure on rare cases when the test
    // is performed on midnight of day, so two weekNums got different values
    // because got calculated in different days
    if (actualWeekNum != expectedWeekNum) {
      actualWeekNum = WeekService.getNextWeekNum();
      expectedWeekNum = WeekService.getWeekNumFrom(
          LocalDate.now(WeekService.getZoneId()).plusDays(7));
    }

    assertThat(actualWeekNum).isEqualTo(expectedWeekNum);
  }

  @Test
  void differentDaysOfSameWeekHaveSameWeekNumTest() {
    LocalDate date1 = LocalDate.of(2022, 11, 24);
    LocalDate date2 = LocalDate.of(2022, 11, 25);

    int actualWeekNum = WeekService.getWeekNumFrom(date1);
    int expectedWeekNum = WeekService.getWeekNumFrom(date2);

    assertThat(actualWeekNum).isEqualTo(expectedWeekNum);
  }

  @Test
  void weekStartsOnMondayTest() {
    LocalDate date1 = LocalDate.of(2022, 11, 23); //sunday
    LocalDate date2 = LocalDate.of(2022, 11, 24); //monday

    int actualWeekNum = WeekService.getWeekNumFrom(date1);
    int expectedWeekNum = WeekService.getWeekNumFrom(date2);

    assertThat(actualWeekNum).isEqualTo(expectedWeekNum);
  }

  @Test
  void isoYearChangeWeekNumInNextYearTest() {
    // By ISO-8601 weekNum belongs to year with more days of that week
    // for 2019->2020, 2 days in 2019 and 5 days in 2020 so weekNum belongs to 2020
    LocalDate date1 = LocalDate.of(2019, 12, 31);
    LocalDate date2 = LocalDate.of(2020, 1, 1);

    int prevYearWeekNum = WeekService.getWeekNumFrom(date1);
    int nextYearWeekNum = WeekService.getWeekNumFrom(date2);
    int actualYear = prevYearWeekNum / 100;

    assertThat(prevYearWeekNum).isEqualTo(nextYearWeekNum);
    assertThat(actualYear).isEqualTo(2020);
  }

  @Test
  void isoYearChangeWeekNumInPrevYearTest() {
    // By ISO-8601 weekNum belongs to year with more days of that week
    // for 2026->2027, 4 days in 2026 and 3 days in 2027 so weekNum belongs to 2026
    LocalDate date1 = LocalDate.of(2026, 12, 31); //saturday
    LocalDate date2 = LocalDate.of(2027, 1, 1);   //monday

    int prevYearWeekNum = WeekService.getWeekNumFrom(date1);
    int nextYearWeekNum = WeekService.getWeekNumFrom(date2);
    int actualYear = prevYearWeekNum / 100;

    assertThat(prevYearWeekNum).isEqualTo(nextYearWeekNum);
    assertThat(actualYear).isEqualTo(2026);
  }

  @Test
  void getDateFromWeekAndDayOfWeekTest() {
    int weekNum = 202204;
    int dayOfWeek = 3;
    LocalDate date = LocalDate.of(2022, Month.JANUARY, 26);
    LocalDate dateIsNotCorrect = LocalDate.of(2022, Month.JANUARY, 27);

    assertThat(date).isEqualTo(WeekService.getDateFromWeekAndDay(weekNum, dayOfWeek));
    assertThat(dateIsNotCorrect).isNotEqualTo(
        WeekService.getDateFromWeekAndDay(weekNum, dayOfWeek));
  }
}
