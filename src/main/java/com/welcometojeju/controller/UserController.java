package com.welcometojeju.controller;

import com.welcometojeju.dto.UserDTO;
import com.welcometojeju.dto.UserInfoDTO;
import com.welcometojeju.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

  @GetMapping("/login")
  public String login() {
    return "user/login";
  }

  @GetMapping("/logout")
  public String logout() {
    return "redirect:/";
  }

  @GetMapping("/myInfo")
  public String getUser(Authentication authentication, Model model) {
    UserDTO user = getUserDTOByProviderId(authentication);

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

    UserDTO user = getUserDTOByProviderId(authentication);

    log.info("[updateUser > post > user] " + user);

    user.setNickname(userInfoDTO.getNickname());
    userService.updateUser(user);

    return "redirect:/";
  }

  private UserDTO getUserDTOByProviderId(Authentication authentication) {
    // 현재 인증된 사용자 정보 가져오기
    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

    log.info("[getUserDTOByProviderId > oAuth2User] " + oAuth2User);

    UserDTO userDTO = userService.getUserByProviderId(oAuth2User.getName());

    log.info("[getUserDTOByProviderId > userDTO] " + userDTO);

    return userDTO;
  }

}
