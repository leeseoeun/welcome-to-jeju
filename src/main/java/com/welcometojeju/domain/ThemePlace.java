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

  @ManyToOne
  @JoinColumn(name = "theme_id")
  private Theme theme;

  @ManyToOne
  @JoinColumn(name = "place_id")
  private Place place;

}
