package com.welcometojeju.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThemeDTO {

  private Integer no;

  @NotEmpty
  @Size(min = 1, max = 255)
  private String title;

  private Integer userNo;

  private int isPublic;

  private int isShare;

  @NotEmpty
  @Size(min = 1, max = 255)
  private String emoji;

  @PositiveOrZero
  private int viewCount;

  private String userNickname;

}
