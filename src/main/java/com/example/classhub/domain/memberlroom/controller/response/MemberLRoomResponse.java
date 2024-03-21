package com.example.classhub.domain.memberlroom.controller.response;

import com.example.classhub.domain.memberlroom.MemberLRoom;
import com.example.classhub.domain.memberlroom.dto.MemberLRoomDto;
import com.example.classhub.domain.memberlroom.dto.Permission;
import com.example.classhub.domain.memberlroom.dto.Role;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLRoomResponse {
  private Role role;
  private Permission permission;

  public MemberLRoomResponse(MemberLRoom memberLRoom) {
    this.role = memberLRoom.getRole();
    this.permission = memberLRoom.getPermission();
  }

  public MemberLRoomResponse(MemberLRoomDto memberLRoomDto) {
    this.role = memberLRoomDto.getRole();
    this.permission = memberLRoomDto.getPermission();
  }
}
