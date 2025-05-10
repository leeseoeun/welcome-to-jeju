package com.welcometojeju.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Place extends BaseEntity {

  @Id
  private Integer no;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private String phone;

  @Column(nullable = false)
  private String x;

  @Column(nullable = false)
  private String y;

  @Builder.Default
  private int registerCount = 0;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_no")
  private User user;

  public void incrementRegisterCount() {
    this.registerCount += 1;
  }

}
