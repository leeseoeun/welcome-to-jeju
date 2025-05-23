package com.welcometojeju.service;

import com.welcometojeju.dto.NotificationDTO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseEmitterService {

  SseEmitter createSseEmitter(Integer userNo);

  void sendToUser(NotificationDTO notificationDTO);

}
