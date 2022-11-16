package com.waterfogsw.eventmonitor.event.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.waterfogsw.eventmonitor.event.api.dto.EventType;
import com.waterfogsw.eventmonitor.event.api.dto.TimeType;

@Component
public class KeyPatternFactory {

  public List<String> getKeyPatternsByTimeAndType(
      TimeType timeType,
      int range,
      EventType type
  ) {
    LocalDateTime endAt = LocalDateTime.now();
    LocalDateTime startAt = null;

    switch (timeType) {
      case DAY -> startAt = endAt.minusDays(range);
      case HOUR -> startAt = endAt.minusHours(range);
      case MINUTE -> startAt = endAt.minusMinutes(range);
    }

    return getTimeKeyPatternBetween(startAt, endAt).parallelStream()
                                                   .map(s -> s + "::" + type)
                                                   .toList();
  }

  public List<String> getTimeKeyPatternBetween(
      LocalDateTime start,
      LocalDateTime end
  ) {
    List<String> result = new ArrayList<>();
    LocalDateTime time = start;
    while (end.isAfter(time)) {
      result.add(time.toString()
                     .substring(0, 16) + "*");
      time = time.plusMinutes(1);
    }
    return result;
  }

}
