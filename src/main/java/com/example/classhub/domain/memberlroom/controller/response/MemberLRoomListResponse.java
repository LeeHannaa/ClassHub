package com.example.classhub.domain.memberlroom.controller.response;

import java.util.List;

public class MemberLRoomListResponse {
  private List<MemberLRoomResponse> memberLRooms;

  public MemberLRoomListResponse(List<MemberLRoomResponse> memberLRooms){
    this.memberLRooms = memberLRooms;
  }
}
