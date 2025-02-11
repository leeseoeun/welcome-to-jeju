package com.welcometojeju.service;

import com.welcometojeju.domain.Place;
import com.welcometojeju.domain.Theme;
import com.welcometojeju.domain.User;
import com.welcometojeju.dto.PlaceDTO;
import com.welcometojeju.dto.ThemeDTO;
import com.welcometojeju.dto.UserDTO;

import java.util.List;

public interface PlaceService {

  Integer createPlace(PlaceDTO placeDTO, User user);
  Integer createPlaceAndRelations(PlaceDTO placeDTO);

  void deletePlaceAndRelations(Integer no, Integer themeNo, Integer userNo);

  List<PlaceDTO> getTop3PlacesByRegisterCount();

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

  default PlaceDTO entityToDto(Place place) {
    PlaceDTO placeDTO = PlaceDTO.builder()
        .no(place.getNo())
        .name(place.getName())
        .address(place.getAddress())
        .phone(place.getPhone())
        .x(place.getX())
        .y(place.getY())
        .registerCount(place.getRegisterCount())
        .userNo(place.getUser().getNo())
        .build();

    return placeDTO;
  }

}
