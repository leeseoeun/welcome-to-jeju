package com.welcometojeju.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserShareTheme extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer no;

  @ManyToOne
  @JoinColumn(name = "user_no")
  private User user;

  @ManyToOne
  @JoinColumn(name = "theme_no")
  private Theme theme;

}
