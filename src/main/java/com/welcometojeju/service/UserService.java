package com.welcometojeju.service;

import com.welcometojeju.domain.User;
import com.welcometojeju.dto.UserDTO;

import java.util.List;

public interface UserService {

  Integer updateUser(UserDTO userDTO);

  UserDTO getUserByProviderId(String providerId);

  List<UserDTO> getAllUsersByKeyword(String keyword);

  default User dtoToEntity(UserDTO userDTO) {
    User user = User.builder()
        .no(userDTO.getNo())
        .email(userDTO.getEmail())
        .password(userDTO.getPassword())
        .nickname(userDTO.getNickname())
        .viewCount(userDTO.getViewCount())
        .role(userDTO.getRole())
        .provider(userDTO.getProvider())
        .providerId(userDTO.getProviderId())
        .build();

    return user;
  }

  default UserDTO entityToDto(User user) {
    UserDTO userDTO = UserDTO.builder()
        .no(user.getNo())
        .email(user.getEmail())
        .password(user.getPassword())
        .nickname(user.getNickname())
        .viewCount(user.getViewCount())
        .role(user.getRole())
        .provider(user.getProvider())
        .providerId(user.getProviderId())
        .build();

    return userDTO;
  }

}
