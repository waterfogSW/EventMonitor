package com.waterfogsw.eventmonitor.event.domain;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.waterfogsw.eventmonitor.event.api.dto.EventPublishRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventCommandService {

  private final EventCommandRepository eventRepository;

  @Async
  @EventListener
  public void onEvent(EventPublishRequest request) {
    Event event = Event.from(request);
    eventRepository.save(event);
  }

}
