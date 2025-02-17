package com.welcometojeju.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThemePlaceDTO {

  private Integer themeNo;

  private Integer placeNo;

  private PlaceDTO placeDTO;

}
