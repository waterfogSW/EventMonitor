package com.waterfogsw.eventmonitor.event.api.dto;

import com.waterfogsw.eventmonitor.event.domain.Event;

public record EventLookupResponse(
    String time,
    String type,
    String content
) {

  public static EventLookupResponse from(Event event) {
    String[] id = event.getId()
                       .split("::");
    return new EventLookupResponse(id[0], id[1], event.getContent());
  }

}
