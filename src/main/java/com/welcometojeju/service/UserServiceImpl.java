package com.welcometojeju.service;

import com.welcometojeju.domain.User;
import com.welcometojeju.dto.UserDTO;
import com.welcometojeju.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public Integer updateUser(UserDTO userDTO) {
    User user = dtoToEntity(userDTO);

    Integer no = userRepository.save(user).getNo();

    return no;
  }

  @Override
  public UserDTO getUserByProviderId(String providerId) {
    Optional<User> result = userRepository.findByProviderId(providerId);

    if (result.isEmpty()) {
      return null;
    }

    UserDTO userDTO = entityToDto(result.get());

    return userDTO;
  }

  @Override
  public List<UserDTO> getAllUsersByKeyword(String keyword) {
    List<User> result = userRepository.findAllByNicknameContaining(keyword);

    List<UserDTO> users = result.stream()
        .map(user -> entityToDto(user)).collect(Collectors.toList());

    return users;
  }

}
