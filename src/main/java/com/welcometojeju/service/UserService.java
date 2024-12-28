package com.welcometojeju.service;

import com.welcometojeju.domain.User;
import com.welcometojeju.dto.UserDTO;

public interface UserService {

  Integer createUser(UserDTO userDTO);

  UserDTO getUserByNo(Integer no);
  String getUserNicknameByNo(Integer no);

  default User dtoToEntity(UserDTO userDTO) {
    User user = User.builder()
        .no(userDTO.getNo())
        .email(userDTO.getEmail())
        .password(userDTO.getPassword())
        .nickname(userDTO.getNickname())
        .isActive(userDTO.getIsActive())
        .viewCount(userDTO.getViewCount())
        .build();

    return user;
  }

  default UserDTO entityToDto(User user) {
    UserDTO userDTO = UserDTO.builder()
        .no(user.getNo())
        .email(user.getEmail())
        .password(user.getPassword())
        .nickname(user.getNickname())
        .isActive(user.getIsActive())
        .viewCount(user.getViewCount())
        .build();

    return userDTO;
  }

}
