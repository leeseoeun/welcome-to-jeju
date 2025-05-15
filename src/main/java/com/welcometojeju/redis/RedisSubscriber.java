package com.welcometojeju.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.welcometojeju.dto.NotificationDTO;
import com.welcometojeju.service.SseEmitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class RedisSubscriber implements MessageListener {

  private final ObjectMapper objectMapper;
  private final SseEmitterService sseEmitterService;

  @Override
  public void onMessage(Message message, byte[] pattern) {
    String channel = new String(message.getChannel());
    String body = new String(message.getBody());

    log.info("[subscriber > channel] {}", channel);
    log.info("[subscriber > message body] {}", body);

    NotificationDTO notificationDTO = null;
    try {
      notificationDTO = objectMapper.readValue(body, NotificationDTO.class);
    } catch (JsonProcessingException error) {
      log.error("[subscriber > error] ", error.getMessage());
    }

    sseEmitterService.sendToUser(notificationDTO);
  }

//  public void onMessage(Object message) {
//    log.info("[subscriber > message] {}", message);
//  }

}
