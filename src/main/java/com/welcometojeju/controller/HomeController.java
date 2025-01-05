package com.welcometojeju.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/")
public class HomeController {

  @GetMapping
  public String home(Model model, Authentication authentication) {
    // 인증된 사용자 정보 가져오기
    Object principal = authentication.getPrincipal(); // UserDetails 객체
    String username = ((org.springframework.security.core.userdetails.User) principal).getUsername();

    log.info("[home > username] " + username);

    model.addAttribute("user", username);

    return "home";
  }
}
