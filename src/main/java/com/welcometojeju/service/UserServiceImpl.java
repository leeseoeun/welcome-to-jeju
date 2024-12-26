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
}
