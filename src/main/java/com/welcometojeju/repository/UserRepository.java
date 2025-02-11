package com.welcometojeju.repository;

import com.welcometojeju.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByProviderId(String  providerId);
  Optional<User> findByEmail(String email);

  List<User> findAllByNicknameContaining(String keyword);

  List<User> findTop3ByOrderByViewCountDesc();

}
