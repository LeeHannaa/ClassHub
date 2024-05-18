package com.example.classhub.domain.memberlroom.dto;

import com.example.classhub.domain.memberlroom.ClassHub_MemberLRoom;
import com.example.classhub.domain.memberlroom.controller.request.MemberLRoomCreateRequest;
import com.example.classhub.domain.memberlroom.controller.request.MemberLRoomMemberCreateRequest;
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

  public static MemberLRoomDto from(ClassHub_MemberLRoom classHubMemberLRoom) {
    return MemberLRoomDto.builder()
      .memberLRoomId(classHubMemberLRoom.getId())
      .role(classHubMemberLRoom.getRole())
      .permission(classHubMemberLRoom.getPermission())
      .build();
  }

  public static MemberLRoomDto from(MemberLRoomMemberCreateRequest memberLRoomMemberCreateRequest){
    return MemberLRoomDto.builder()
//            .memberLRoomId(memberLRoomMemberCreateRequest.getUniqueId());
            .build();
  }


}
