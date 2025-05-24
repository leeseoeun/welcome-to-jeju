package com.welcometojeju.config;

import com.welcometojeju.redis.RedisChannel;
import com.welcometojeju.redis.RedisSubscriber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

  @Value("${spring.data.redis.host}")
  private String host;

  @Value("${spring.data.redis.port}")
  private int port;

  @Value("${spring.data.redis.password}")
  private String password;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
    redisStandaloneConfiguration.setHostName(host);
    redisStandaloneConfiguration.setPort(port);
    redisStandaloneConfiguration.setPassword(RedisPassword.of(password));

    return new LettuceConnectionFactory(redisStandaloneConfiguration);
  }

  // Redis 데이터를 저장하거나 조회할 때 사용
  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);

    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());

    GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
    redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);  // new Jackson2JsonRedisSerializer<>(Object.class)
    redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
    redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);

    // 프로퍼티 설정 완료 후 초기화 호출
    redisTemplate.afterPropertiesSet();

    return redisTemplate;
  }

  // Redis에서 Pub/Sub 메시지를 수신할 수 있도록 설정
  @Bean
  public RedisMessageListenerContainer redisMessageListenerContainer(
      RedisConnectionFactory redisConnectionFactory,
      RedisSubscriber redisSubscriber
  ) {
    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(redisConnectionFactory); // Redis 연결 설정
    container.addMessageListener(redisSubscriber, new ChannelTopic(RedisChannel.NOTIFICATION.getTopic()));

    return container;
  }

}
