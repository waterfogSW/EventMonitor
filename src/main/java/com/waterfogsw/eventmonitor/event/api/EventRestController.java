package com.waterfogsw.eventmonitor.event.api;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.waterfogsw.eventmonitor.event.api.dto.EventLookupRequest;
import com.waterfogsw.eventmonitor.event.api.dto.EventLookupResponse;
import com.waterfogsw.eventmonitor.event.api.dto.EventPublishRequest;
import com.waterfogsw.eventmonitor.event.domain.EventQueryRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EventRestController {

  private final ApplicationEventPublisher eventPublisher;
  private final EventQueryRepository eventQueryRepository;

  @PostMapping("events")
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@RequestBody EventPublishRequest request) {
    eventPublisher.publishEvent(request);
  }

  @GetMapping("events")
  @ResponseStatus(HttpStatus.OK)
  public List<EventLookupResponse> search(@ModelAttribute EventLookupRequest request) {
    request.validateTimeRange();

    return eventQueryRepository.lookup(request)
                               .stream()
                               .map(EventLookupResponse::from)
                               .sorted(Comparator.comparing(EventLookupResponse::time))
                               .collect(Collectors.toList());
  }

}
