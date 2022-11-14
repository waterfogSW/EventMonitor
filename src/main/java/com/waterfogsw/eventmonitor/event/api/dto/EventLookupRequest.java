package com.waterfogsw.eventmonitor.event.api.dto;

public record EventLookupRequest(
    EventType type,
    TimeType timeType,
    int timeRange
) {

  public void validateTimeRange() {
    if (timeRange > timeType.getMaxRange() || timeRange <= 0) {
      throw new IllegalArgumentException();
    }
  }

}
