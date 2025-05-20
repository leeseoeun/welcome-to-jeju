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
@Table(name = "user", indexes = {
    @Index(name = "idx_email", columnList = "email", unique = true)
})
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer no;

  @Column(length = 255, nullable = false, unique = true)
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

  public void incrementViewCount() {
    this.viewCount += 1;
  }

}
