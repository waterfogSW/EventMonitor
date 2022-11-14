package com.waterfogsw.eventmonitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "redis")
public record RedisProperties(
    String host,
    int port
) {

}
