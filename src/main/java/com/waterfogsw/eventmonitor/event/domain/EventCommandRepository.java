package com.waterfogsw.eventmonitor.event.domain;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EventCommandRepository {

  private final RedisTemplate<String, Event> redisTemplate;

  public void save(Event event) {
    ValueOperations<String, Event> operations = redisTemplate.opsForValue();
    operations.set(event.getId(), event);
  }

}
