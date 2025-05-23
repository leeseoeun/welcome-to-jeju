package com.welcometojeju.repository;

import com.welcometojeju.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByProviderId(String  providerId);
  Optional<User> findByEmail(String email);

  List<User> findTop3ByOrderByViewCountDesc();

  @Query(value = "SELECT * FROM user WHERE MATCH(nickname) AGAINST (?1 IN BOOLEAN MODE)", nativeQuery = true)
  List<User> searchByNickname(String keyword);

}
