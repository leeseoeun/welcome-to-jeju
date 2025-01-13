package com.welcometojeju.security;

import com.welcometojeju.domain.User;
import com.welcometojeju.repository.UserRepository;
import com.welcometojeju.security.oauth.KakaoOAuth2UserInfo;
import com.welcometojeju.security.oauth.NaverOAuth2UserInfo;
import com.welcometojeju.security.oauth.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    log.info("[loadUser > userRequest] " + userRequest);

    ClientRegistration clientRegistration = userRequest.getClientRegistration();
    String clientName = clientRegistration.getClientName();

    log.info("[loadUser > clientName] " + clientName);

    OAuth2User oAuth2User = super.loadUser(userRequest);
    Map<String, Object> attributeMap = oAuth2User.getAttributes();

    attributeMap.forEach((k, v) -> {
      log.info("[loadUser > attributeMap] " + k + " : " + v);
    });

    OAuth2UserInfo userInfo = null;

    switch (clientName) {
      case "kakao":
        userInfo = new KakaoOAuth2UserInfo(attributeMap);
        break;
      case "naver":
        userInfo = new NaverOAuth2UserInfo(attributeMap);
        break;
    }

    log.info("[loadUser > userInfo] " + userInfo);

    Optional<User> result = userRepository.findByEmail(userInfo.getEmail());

    log.info("[loadUser > result] " + result);

    if (result.isEmpty()) {
      User user = User.builder()
          .email(userInfo.getEmail())
          .password(passwordEncoder.encode("wtj"))
          .nickname(userInfo.getNickname())
          .role("ROLE_USER")
          .provider(userInfo.getProvider())
          .providerId(userInfo.getProviderId())
          .build();

      userRepository.save(user);
    }

    return new DefaultOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
        userInfo.getAttributes(),
        "id" // 기본 username attribute
    );
  }

}
