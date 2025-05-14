package com.welcometojeju.service; 

import com.welcometojeju.dto.NotificationDTO;

public interface RedisService {

  void publish(NotificationDTO notificationDTO);

}
