package com.welcometojeju.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationDTO {

  private String themeTitle;
  private String message;

  public NotificationDTO(String themeTitle) {
    this.themeTitle = themeTitle;
    this.message = String.format("[%s] 테마에 장소가 등록되었습니다!", themeTitle);
  }

}
