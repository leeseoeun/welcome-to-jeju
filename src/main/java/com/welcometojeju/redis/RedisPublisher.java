package com.welcometojeju.redis;

import com.welcometojeju.dto.NotificationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class RedisPublisher {

  private final RedisTemplate<String, Object> redisTemplate;
  private final ChannelTopic channelTopic;

  public void publish(NotificationDTO notificationDTO) {
    log.info("[publish > topic] {}", channelTopic.getTopic());
    log.info("[publish > message] {}", notificationDTO.getMessage());

    redisTemplate.convertAndSend(channelTopic.getTopic(), notificationDTO);
  }

}
