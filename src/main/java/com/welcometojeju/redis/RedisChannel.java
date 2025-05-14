package com.welcometojeju.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisChannel {

  NOTIFICATION("notification");

  private final String topic;

}
