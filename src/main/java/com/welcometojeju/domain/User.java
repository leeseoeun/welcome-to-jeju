package com.welcometojeju.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User extends BaseEntity implements Comparable<User> {

  @Id
  @GeneratedValue
  private Integer no;

  @Column(length = 255, nullable = false)
  private String email;

  @Column(length = 60, nullable = false)
  private String password;

  @Column(length = 20, nullable = false)
  private String nickname;

  @Builder.Default
  private int isActive = 1;

  @Builder.Default
  private int viewCount = 0;

  @Override
  public int compareTo(User user) {
    return user.viewCount - this.viewCount;
  }
  
}
