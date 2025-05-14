package com.welcometojeju.redis;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RedisSubscriber implements MessageListener {

  @Override
  public void onMessage(Message message, byte[] pattern) {
    String channel = new String(message.getChannel());
    String body = new String(message.getBody());

    log.info("[subscriber > channel] {}", channel);
    log.info("[subscriber > message body] {}", body);
  }

//  public void onMessage(Object message) {
//    log.info("[subscriber > message] {}", message);
//  }

}
