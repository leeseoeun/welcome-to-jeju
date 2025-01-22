package com.welcometojeju.service;

import com.welcometojeju.domain.Place;
import com.welcometojeju.domain.User;
import com.welcometojeju.dto.PlaceDTO;
import com.welcometojeju.repository.PlaceRepository;
import com.welcometojeju.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class PlaceServiceImpl implements PlaceService {

  private final PlaceRepository placeRepository;
  private final UserRepository userRepository;

  @Override
  public Integer createPlace(PlaceDTO placeDTO) {
    Optional<User> result = userRepository.findById(placeDTO.getUserNo());

    User user = result.orElseThrow();

    Place place = dtoToEntity(placeDTO, user);

    Integer no = placeRepository.save(place).getNo();

    return no;
  }

}
