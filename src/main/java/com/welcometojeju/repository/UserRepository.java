package com.welcometojeju.repository;

import com.welcometojeju.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);

  Optional<User> findByNickname(String nickname);

  @Query("SELECT u.nickname FROM User u WHERE u.no = :no")
  String findNicknameByNo(Integer no);

}
