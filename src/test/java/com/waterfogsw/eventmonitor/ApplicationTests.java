package com.waterfogsw.eventmonitor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.waterfogsw.eventmonitor.event.api.dto.TimeType;

@SpringBootTest
class ApplicationTests {

  @Test
  void contextLoads() {
    List<String> timePatterns = getTimeKeyPatterns(TimeType.MINUTE, 10L);
    System.out.println(timePatterns);
  }

  private List<String> getTimeKeyPatterns(
      TimeType type,
      Long range
  ) {
    LocalDateTime startTime = LocalDateTime.now();
    LocalDateTime endTime = null;

    switch (type) {
      case DAY -> endTime = startTime.plusDays(range);
      case HOUR -> endTime = startTime.plusHours(range);
      case MINUTE -> endTime = startTime.plusMinutes(range);
    }

    List<String> result = new ArrayList<>();
    LocalDateTime time = startTime;

    while (endTime.isAfter(time)) {
      result.add(time.toString().substring(0, 16));
      time = time.plusMinutes(1);
    }

    return result;
  }

}
