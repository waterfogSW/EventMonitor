package com.waterfogsw.eventmonitor.event.api.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TimeType {

  DAY(7),
  HOUR(7 * 24),
  MINUTE(7 * 24 * 60);

  private final int maxRange;
}
