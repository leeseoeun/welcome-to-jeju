package com.welcometojeju.dto;

import com.welcometojeju.domain.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

  private int no;

  @NotEmpty
  @Size(min = 1, max = 255)
  private String title;

  @NotNull
  private User user;

  @NotNull
  private int categoryNo;

  @NotNull
  private int isPublic;

  @NotNull
  private int isShare;

  @NotEmpty
  @Size(min = 1, max = 255)
  private String emoji;

  @PositiveOrZero
  private int viewCount;

}
