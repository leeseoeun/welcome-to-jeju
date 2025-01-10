package com.welcometojeju.controller;

import com.welcometojeju.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @GetMapping("/login")
  public String login() {
    return "user/login";
  }

  @GetMapping("/logout")
  public String logout() {
    return "redirect:/";
  }

}
