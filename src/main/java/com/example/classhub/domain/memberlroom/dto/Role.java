package com.example.classhub.domain.memberlroom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
  STUDENT("학생"),
  TA("조교"),
  PROFESSOR("교수");

  private final String role;

  public static Role from(String feature) {
    for (Role r : Role.values()) {
      if (r.role.equals(feature)) {
        return r;
      }
    }
    throw new IllegalArgumentException();
  }
}
