package com.waterfogsw.eventmonitor.config;

import static io.lettuce.core.ReadFrom.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.waterfogsw.eventmonitor.event.domain.Event;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfig {

  private final RedisProperties redisProperties;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                                                                        .readFrom(REPLICA_PREFERRED)
                                                                        .build();
    RedisInfo masterInfo = redisProperties.master();
    RedisInfo slaveInfo = redisProperties.slave();

    RedisStaticMasterReplicaConfiguration redisConfig =
        new RedisStaticMasterReplicaConfiguration(masterInfo.host(), masterInfo.port());

    redisConfig.addNode(slaveInfo.host(), slaveInfo.port());

    return new LettuceConnectionFactory(redisConfig, clientConfig);
  }

  @Bean
  public RedisTemplate<String, Event> redisTemplate() {
    RedisTemplate<String, Event> redisTemplate = new RedisTemplate<>();

    redisTemplate.setConnectionFactory(redisConnectionFactory());
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Event.class));

    return redisTemplate;
  }

}
