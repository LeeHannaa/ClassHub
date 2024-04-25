package com.example.classhub.domain.memberlroom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {
  APPROVED("입장"),
  UNAPPROVED("미입장"),
  BLOCKED("거절");

  private final String permission;

  public static Permission from(String permission) {
    for (Permission p : Permission.values()) {
      if (p.permission.equals(permission)) {
        return p;
      }
    }
    throw new IllegalArgumentException();
  }
}
