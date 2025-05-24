package com.welcometojeju.service;

import com.welcometojeju.dto.NotificationDTO;
import com.welcometojeju.redis.RedisCacheKey;

import java.util.List;

public interface RedisService {

  // Pub/Sub
  void publish(NotificationDTO notificationDTO);

  // Cache
  void setCache(RedisCacheKey redisCacheKey, Object data);

  <T> List<T> getListCache(RedisCacheKey redisCacheKey, Class<T> clazz);

  void evictCache(RedisCacheKey redisCacheKey);

}
