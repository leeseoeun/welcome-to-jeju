package com.welcometojeju.service;

import com.welcometojeju.domain.User;
import com.welcometojeju.dto.UserDTO;
import com.welcometojeju.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public Integer createUser(UserDTO userDTO) {
    User user = dtoToEntity(userDTO);

    Integer no = userRepository.save(user).getNo();

    return no;
  }

  @Override
  public UserDTO getUserByNo(Integer no) {
    Optional<User> result = userRepository.findById(no);

    User user = result.orElseThrow();

    UserDTO userDTO = entityToDto(user);

    return userDTO;
  }

  @Override
  public UserDTO getUserByEmail(String email) {
    Optional<User> result = userRepository.findByEmail(email);

    if (result.isEmpty()) {
      return null;
    }

    UserDTO userDTO = entityToDto(result.get());

    return userDTO;
  }

  @Override
  public UserDTO getUserByNickname(String nickname) {
    Optional<User> result = userRepository.findByNickname(nickname);

    if (result.isEmpty()) {
      return null;
    }

    UserDTO userDTO = entityToDto(result.get());

    return userDTO;
  }

  @Override
  public String getUserNicknameByNo(Integer no) {
    return userRepository.findNicknameByNo(no);
  }
}
