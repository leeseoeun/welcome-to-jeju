package com.welcometojeju.security.oauth;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {

  public NaverOAuth2UserInfo(Map<String, Object> attributes) {
    super((Map<String, Object>) attributes.get("response"));
  }

  @Override
  public String getProviderId() {
    return attributes.get("id").toString();
  }

  @Override
  public String getEmail() {
    return attributes.get("email").toString();
  }

  @Override
  public String getNickname() {
    return attributes.get("nickname").toString();
  }

  @Override
  public String getProvider() {
    return "naver";
  }

}
