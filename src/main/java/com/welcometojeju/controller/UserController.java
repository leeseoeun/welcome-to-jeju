package com.welcometojeju.controller;

import com.welcometojeju.dto.UserDTO;
import com.welcometojeju.dto.UserInfoDTO;
import com.welcometojeju.security.SecurityUtils;
import com.welcometojeju.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/users")
public class UserController {

  private final UserService userService;
  private final SecurityUtils securityUtils;

  @GetMapping("/login")
  public String login() {
    return "user/login";
  }

  @GetMapping("/logout")
  public String logout() {
    return "redirect:/";
  }

  @GetMapping("/myInfo")
  public String getUser(Model model) {
    UserDTO user = securityUtils.getAuthenticatedUser();

    log.info("[getMyInfo > user] " + user);

    model.addAttribute("user", user);

    return "user/my-info";
  }

  @PostMapping("/update")
  public String updateUser(Authentication authentication, @Valid UserInfoDTO userInfoDTO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      log.info("[updateUser > post > error] " + bindingResult);

      return "redirect:user/my-info";
    }

    log.info("[updateUser > post > user] " + userInfoDTO);

    UserDTO user = securityUtils.getAuthenticatedUser();

    log.info("[updateUser > post > user] " + user);

    user.setNickname(userInfoDTO.getNickname());
    userService.updateUser(user);

    return "redirect:/";
  }

}
