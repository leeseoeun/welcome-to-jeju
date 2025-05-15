package com.welcometojeju.service;

import com.welcometojeju.dto.NotificationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Log4j2
public class SseEmitterServiceImpl implements SseEmitterService {

  // 멀티 스레드 환경에서 thread-safe 하기 때문에 ConcurrentHashMap 사용
  private final Map<Integer, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

  @Override
  public SseEmitter createSseEmitter(Integer userNo) {
    log.info("[createSseEmitter > userNo] {}", userNo);

    SseEmitter sseEmitter = new SseEmitter(60L * 1000 * 60);  // 1시간
    sseEmitters.put(userNo, sseEmitter);

    sseEmitter.onCompletion(() -> sseEmitters.remove(userNo));
    sseEmitter.onError((error) -> {
      sseEmitters.remove(userNo);

      log.error("[createSseEmitter > error] ", error);
    });
    sseEmitter.onTimeout(() -> sseEmitters.remove(userNo));

    return sseEmitter;
  }

  @Override
  public void sendToUser(NotificationDTO notificationDTO) {
    SseEmitter sseEmitter = sseEmitters.get(notificationDTO.getReceiverId());
    if (sseEmitter != null) {
      try {
        sseEmitter.send(SseEmitter.event()
            .name("notification")
            .data(notificationDTO));

        log.info("[sendToUser > message] {}", notificationDTO.getMessage());
        log.info("[sendToUser > receiverId] {}", notificationDTO.getReceiverId());
      } catch (IOException error) {
        // 전송 중 에러가 발생하면 해당 emitter 제거
        sseEmitters.remove(notificationDTO.getReceiverId());

        log.error("[sendToUser > error] ", error.getMessage());
      }
    }
  }

}
