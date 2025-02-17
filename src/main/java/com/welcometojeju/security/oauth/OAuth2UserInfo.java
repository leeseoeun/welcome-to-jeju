package com.welcometojeju.security.oauth;

import java.util.Map;

public abstract class OAuth2UserInfo {
  protected Map<String, Object> attributes;

  public OAuth2UserInfo(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  public abstract String getProviderId();
  public abstract String getEmail();
  public abstract String getNickname();
  public abstract String getProvider();

  public Map<String, Object> getAttributes() {
    return attributes;
  }

}
