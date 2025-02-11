package com.welcometojeju.service;

import com.welcometojeju.domain.Place;
import com.welcometojeju.domain.User;
import com.welcometojeju.dto.PlaceDTO;

import java.util.List;

public interface PlaceService {

  Integer createPlace(PlaceDTO placeDTO, User user);
  Integer createPlaceAndRelations(PlaceDTO placeDTO);

  void deletePlaceAndRelations(Integer no, Integer themeNo, Integer userNo);

  default Place dtoToEntity(PlaceDTO placeDTO, User user) {
    Place place = Place.builder()
        .no(placeDTO.getNo())
        .name(placeDTO.getName())
        .address(placeDTO.getAddress())
        .x(placeDTO.getX())
        .y(placeDTO.getY())
        .phone(placeDTO.getPhone())
        .registerCount(placeDTO.getRegisterCount())
        .user(user)
        .build();

    return place;
  }

}
