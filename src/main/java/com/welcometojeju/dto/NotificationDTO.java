package com.welcometojeju.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationDTO {

  private String themeTitle;
  private String message;
  private Integer receiverId;

  public NotificationDTO(String themeTitle, Integer receiverId) {
    this.themeTitle = themeTitle;
    this.message = String.format("[%s] 테마에 장소가 등록되었습니다!", themeTitle);
    this.receiverId = receiverId;
  }

}
