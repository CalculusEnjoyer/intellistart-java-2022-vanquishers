package com.intellias.intellistart.interviewplanning.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WeekServiceTest {

  GregorianCalendar calendar = new GregorianCalendar(2022, 9, 20);

  @Test
  void getCurrentWeekNumInFormat() {
    calendar.setFirstDayOfWeek(Calendar.MONDAY);
    calendar.setMinimalDaysInFirstWeek(7);
    WeekService weekServiceSpy = spy(WeekService.class);
    when(weekServiceSpy.getCalendar()).thenReturn(calendar);

    assertThat(weekServiceSpy.getCurrentWeekNumInFormat()).isEqualTo(202242);
  }

  @Test
  void getNextWeekNumInFormat() {
    calendar.setFirstDayOfWeek(Calendar.MONDAY);
    calendar.setMinimalDaysInFirstWeek(7);
    WeekService weekServiceSpy = spy(WeekService.class);
    when(weekServiceSpy.getNextWeekCalendar()).thenReturn(calendar);

    assertThat(weekServiceSpy.getNextWeekNumInFormat()).isEqualTo(202242);
  }
}
