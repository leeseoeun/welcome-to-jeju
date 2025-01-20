package com.welcometojeju.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ThemePlace extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer no;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "theme_id")
  private Theme theme;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "place_id")
  private Place place;

}
