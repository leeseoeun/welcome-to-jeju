package com.welcometojeju.redis;

import java.time.Duration;

public enum RedisCacheKey {

  TOP_USERS("top_users", Duration.ofHours(1)),
  TOP_PUBLIC_THEMES("top_public_themes", Duration.ofHours(1)),
  TOP_COLLABORATE_THEMES("top_collaborate_themes", Duration.ofHours(1)),
  TOP_PLACES("top_places", Duration.ofHours(1));

  private final String key;
  private final Duration ttl;

  RedisCacheKey(String key, Duration ttl) {
    this.key = key;
    this.ttl = ttl;
  }

  public String getKey() {
    return key;
  }

  public Duration getTtl() {
    return ttl;
  }

}
