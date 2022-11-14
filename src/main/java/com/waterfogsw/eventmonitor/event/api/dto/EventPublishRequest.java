package com.waterfogsw.eventmonitor.event.api.dto;

public record EventPublishRequest(
    String type,
    String content
) {

}
