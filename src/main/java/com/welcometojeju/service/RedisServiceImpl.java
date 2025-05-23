package com.welcometojeju.service;

import com.welcometojeju.dto.NotificationDTO;
import com.welcometojeju.redis.RedisChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class RedisServiceImpl implements RedisService {

  private final RedisTemplate<String, Object> redisTemplate;

  @Override
  public void publish(NotificationDTO notificationDTO) {
    log.info("[publish > message] {}", notificationDTO.getMessage());

    redisTemplate.convertAndSend(RedisChannel.NOTIFICATION.getTopic(), notificationDTO);
  }

}
