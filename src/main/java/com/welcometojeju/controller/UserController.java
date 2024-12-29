package com.welcometojeju.controller;

import com.welcometojeju.domain.User;
import com.welcometojeju.dto.ThemeDTO;
import com.welcometojeju.dto.UserDTO;
import com.welcometojeju.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @GetMapping("/create")
  public String createUser(Model model) {
    return "user/create";
  }

  @PostMapping("/create")
  public ResponseEntity<Map<String, String>> createUser(@Valid UserDTO userDTO, BindingResult bindingResult) {
    Map<String, String> response = new HashMap<>();

    if (bindingResult.hasErrors()) {
      log.info("[createUser > post > error]" + bindingResult);

      response.put("redirectUrl", "/users/create");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

    log.info("[createUser > post > user] " + userDTO);

    UserDTO user = userService.getUserByEmail(userDTO.getEmail());
    log.info("[createUser > post > getUserByEmail] " + user);
    if (user != null) {
      response.put("error", "이미 사용 중인 이메일입니다.");
      return ResponseEntity.ok(response);
    }

    user = userService.getUserByNickname(userDTO.getNickname());
    log.info("[createUser > post > getUserByEmail] " + user);
    if (user != null) {
      response.put("error", "이미 사용 중인 닉네임입니다.");
      return ResponseEntity.ok(response);
    }

    Integer no = userService.createUser(userDTO);
    log.info("[createUser > post > no] " + no);

    response.put("redirectUrl", "/users/login");
    return ResponseEntity.ok(response);
  }

}
