package com.example.classhub.domain.memberlroom.controller.response;

import com.example.classhub.domain.memberlroom.dto.MemberLRoomDto;
import com.example.classhub.domain.memberlroom.dto.Permission;
import com.example.classhub.domain.memberlroom.dto.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberLRoomCreateResponse {
  private Role role;
  private Permission permission;

  public MemberLRoomCreateResponse(MemberLRoomDto memberLRoomDto) {
    this.role = memberLRoomDto.getRole();
    this.permission = memberLRoomDto.getPermission();
  }
}
