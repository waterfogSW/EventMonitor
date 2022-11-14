package com.waterfogsw.eventmonitor.event.domain;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import com.waterfogsw.eventmonitor.event.api.dto.EventPublishRequest;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@RedisHash("event")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event {

  @Id
  private String id;

  private String content;

  @TimeToLive(unit = TimeUnit.DAYS)
  private Long expire;

  public static Event from(EventPublishRequest request) {
    String id = LocalDateTime.now() + "::" + request.type();
    return new Event(id, request.content(), 7L);
  }

}
