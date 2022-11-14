package com.waterfogsw.eventmonitor.event.domain;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.waterfogsw.eventmonitor.event.api.dto.EventLookupRequest;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EventQueryRepository {

  private final RedisTemplate<String, Event> redisTemplate;
  private final KeyPatternFactory keyPatternFactory;

  public List<Event> lookup(EventLookupRequest request) {
    List<String> timeKeyPatterns = keyPatternFactory
        .getTimeKeyPatterns(request.timeType(), request.timeRange());

    List<String> keys = findKeysByPatterns(timeKeyPatterns);

    return redisTemplate.opsForValue()
                        .multiGet(keys);
  }

  private List<String> findKeysByPatterns(List<String> keyPatterns) {
    return keyPatterns.parallelStream()
                      .map(redisTemplate::keys)
                      .filter(Objects::nonNull)
                      .flatMap(Set::stream)
                      .collect(Collectors.toList());
  }

}
