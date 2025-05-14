package com.welcometojeju.service;

import com.welcometojeju.dto.NotificationDTO;
import com.welcometojeju.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

  private final RedisPublisher redisPublisher;

  @Override
  public void notifyPlaceRegistered(NotificationDTO notificationDTO) {
    redisPublisher.publish(notificationDTO);
  }

}
