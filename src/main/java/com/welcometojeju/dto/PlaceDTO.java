package com.welcometojeju.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDTO {

  private Integer no;

  @NotEmpty
  private String name;

  @NotEmpty
  private String address;

  @NotEmpty
  private String phone;

  @NotEmpty
  private String x;

  @NotEmpty
  private String y;

  @NotEmpty
  private Integer userNo;

  private Integer themeNo;

}
