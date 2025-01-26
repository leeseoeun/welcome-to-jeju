package com.welcometojeju.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DynamicUpdate
public class User extends BaseEntity implements Comparable<User> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer no;

  @Column(length = 255, nullable = false)
  private String email;

  @Column(length = 60, nullable = false)
  private String password;

  @Column(length = 20, nullable = false)
  private String nickname;

  @Builder.Default
  private int viewCount = 0;

  @Builder.Default
  @Column(length = 20)
  private String role = "ROLE_USER";

  @Column(length = 50, nullable = false)
  private String provider;

  @Column(length = 255, nullable = false)
  private String providerId;

  @Override
  public int compareTo(User user) {
    return user.viewCount - this.viewCount;
  }
  
}
