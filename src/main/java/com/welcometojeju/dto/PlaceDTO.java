package com.welcometojeju.dto;

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

  private String name;

  private String address;

  private String x;

  private String y;

  private Integer userNo;

}
