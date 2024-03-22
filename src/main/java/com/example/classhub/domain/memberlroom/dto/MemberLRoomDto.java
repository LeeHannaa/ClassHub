package com.example.classhub.domain.memberlroom.dto;

import com.example.classhub.domain.memberlroom.MemberLRoom;
import com.example.classhub.domain.memberlroom.controller.request.MemberLRoomCreateRequest;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MemberLRoomDto {
  private Long memberLRoomId;
  private Role role;
  private Permission permission;

  public static MemberLRoomDto from(MemberLRoomCreateRequest memberLRoomCreateRequest) {

    return MemberLRoomDto.builder()
      .role(memberLRoomCreateRequest.getRole())
      .permission(memberLRoomCreateRequest.getPermission())
      .build();
  }

  public static MemberLRoomDto from(MemberLRoom memberLRoom) {
    return MemberLRoomDto.builder()
      .memberLRoomId(memberLRoom.getId())
      .role(memberLRoom.getRole())
      .permission(memberLRoom.getPermission())
      .build();
  }


}
