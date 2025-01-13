package com.welcometojeju.security;

import com.welcometojeju.dto.UserDTO;
import com.welcometojeju.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class SecurityUtils {

  private final UserService userService;

  public UserDTO getAuthenticatedUser() {
    // 현재 인증된 사용자 정보 가져오기
    OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    log.info("[getAuthenticatedUser > oAuth2User] " + oAuth2User);

    UserDTO userDTO = userService.getUserByProviderId(oAuth2User.getName());

    log.info("[getUserDTOByProviderId > userDTO] " + userDTO);

    return userDTO;
  }

}
