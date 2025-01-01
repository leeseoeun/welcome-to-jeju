package com.welcometojeju.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

  @NotEmpty
  @Size(min = 1, max = 255)
  private String email;

  @NotEmpty
  @Size(min = 1, max = 60)
  private String password;

}
