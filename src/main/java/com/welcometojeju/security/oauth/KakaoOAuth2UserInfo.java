package com.welcometojeju.security.oauth;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

  public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
    super(attributes);
  }

  @Override
  public String getProviderId() {
    return attributes.get("id").toString();
  }

  @Override
  public String getEmail() {
    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
    return kakaoAccount.get("email").toString();
  }

  @Override
  public String getNickname() {
    Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
    return properties.get("nickname").toString();
  }

  @Override
  public String getProvider() {
    return "kakao";
  }

}
