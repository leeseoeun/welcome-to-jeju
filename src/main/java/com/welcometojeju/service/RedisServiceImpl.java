package com.welcometojeju.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.welcometojeju.dto.NotificationDTO;
import com.welcometojeju.redis.RedisCacheKey;
import com.welcometojeju.redis.RedisChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class RedisServiceImpl implements RedisService {

  private final RedisTemplate<String, Object> redisTemplate;
  private final ObjectMapper objectMapper;

  // Pub/Sub
  @Override
  public void publish(NotificationDTO notificationDTO) {
    log.info("[publish > message] {}", notificationDTO.getMessage());

    redisTemplate.convertAndSend(RedisChannel.NOTIFICATION.getTopic(), notificationDTO);
  }

  // Cache
  public void setCache(RedisCacheKey redisCacheKey, Object data) {
    redisTemplate.opsForValue().set(redisCacheKey.getKey(), data, redisCacheKey.getTtl());

    log.info("[setCache > key] {}, [ttl] {}", redisCacheKey.getKey(), redisCacheKey.getTtl());
  }

  /**
   * <T>      : 메서드가 제네릭 타입 T를 사용함을 선언
   * List<T>  : 반환 타입은 T 타입 객체들의 리스트
   * Class<T> : T 타입 정보를 전달받기 위한 Class 객체 (예: User.class)
   */
  public <T> List<T> getListCache(RedisCacheKey redisCacheKey, Class<T> clazz) {
    log.info("[getListCache > key] {}", redisCacheKey.getKey());

    Object value = redisTemplate.opsForValue().get(redisCacheKey.getKey());

    if (value == null) return null;

    List<?> valueList = (List<?>) value;

    return valueList.stream().map(item -> objectMapper.convertValue(item, clazz))
        .collect(Collectors.toList());
  }

  @Override
  public void evictCache(RedisCacheKey redisCacheKey) {
    log.info("[evictCache > key] {}", redisCacheKey.getKey());

    redisTemplate.delete(redisCacheKey.getKey());
  }

}
