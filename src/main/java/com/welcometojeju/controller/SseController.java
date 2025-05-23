package com.welcometojeju.controller;

import com.welcometojeju.dto.UserDTO;
import com.welcometojeju.security.SecurityUtils;
import com.welcometojeju.service.SseEmitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/sse")
public class SseController {

  private final SseEmitterService sseEmitterService;
  private final SecurityUtils securityUtils;

  // produces : 반환 데이터 형식
  // TEXT_EVENT_STREAM_VALUE : SSE 전용 MIME 타입
  @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public SseEmitter subscribe() {
    UserDTO userDTO = securityUtils.getAuthenticatedUser();
    log.info("[subscribe > userDTO] " + userDTO);

    return sseEmitterService.createSseEmitter(userDTO.getNo());
  }

}
