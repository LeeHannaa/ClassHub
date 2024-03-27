package com.example.classhub.domain.memberlroom.controller.response;

import com.example.classhub.domain.memberlroom.ClassHub_MemberLRoom;
import com.example.classhub.domain.memberlroom.dto.MemberLRoomDto;
import com.example.classhub.domain.memberlroom.dto.Permission;
import com.example.classhub.domain.memberlroom.dto.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLRoomResponse {
  private Role role;
  private Permission permission;

  public MemberLRoomResponse(ClassHub_MemberLRoom classHubMemberLRoom) {
    this.role = classHubMemberLRoom.getRole();
    this.permission = classHubMemberLRoom.getPermission();
  }

  public MemberLRoomResponse(MemberLRoomDto memberLRoomDto) {
    this.role = memberLRoomDto.getRole();
    this.permission = memberLRoomDto.getPermission();
  }
}
