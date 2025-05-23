package com.welcometojeju.service;

import com.welcometojeju.dto.NotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

  private final RedisService redisService;

  @Override
  public void notifyPlaceRegistered(NotificationDTO notificationDTO) {

    redisService.publish(notificationDTO);
  }

}
