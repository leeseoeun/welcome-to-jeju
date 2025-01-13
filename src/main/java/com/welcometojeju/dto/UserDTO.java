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
public class UserDTO {

  private Integer no;

  @NotEmpty
  @Size(min = 1, max = 255)
  private String email;

  @NotEmpty
  @Size(min = 1, max = 60)
  private String password;

  @NotEmpty
  @Size(min = 1, max = 20)
  private String nickname;

  @PositiveOrZero
  private int viewCount;

  private String role;

  private String provider;
  private String providerId;

}
