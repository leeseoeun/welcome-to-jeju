package com.welcometojeju.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ThemePlace extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer no;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "theme_no")
  private Theme theme;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "place_no")
  private Place place;

}
