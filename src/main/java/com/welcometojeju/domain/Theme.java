package com.welcometojeju.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Theme extends BaseEntity implements Comparable<Theme> {

  @Id
  @GeneratedValue
  private Integer no; // default 값이 없는 경우 참조형으로 선언

  @Column(length = 255, nullable = false)
  private String title;

  @ManyToOne
  @JoinColumn(name = "user_no")
  private User user;

  @Column(nullable = false)
  private Integer categoryNo;

  @Column(nullable = false)
  @Builder.Default
  private int isPublic = 1; // default 값이 있는 경우 기본형으로 선언

  @Column(nullable = false)
  @Builder.Default
  private int isShare = 1;

  @Column(length = 255, nullable = false)
  private String emoji;

  @Builder.Default
  private int viewCount = 0;

  @Override
  public int compareTo(Theme theme) {
    return theme.viewCount - this.viewCount;
  }

}
