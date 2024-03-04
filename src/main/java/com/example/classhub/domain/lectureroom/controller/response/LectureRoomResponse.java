package com.example.classhub.domain.lectureroom.controller.response;

import com.example.classhub.domain.lectureroom.LectureRoom;
import com.example.classhub.domain.lectureroom.dto.LectureRoomDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureRoomResponse {
    private Long lectureRoomId;
    private String name;
    private String taInviteCode;
    private String stInviteCode;
    private boolean onOff;

    public LectureRoomResponse(LectureRoom lectureRoom){
        this.lectureRoomId = lectureRoom.getLectureRoomId();
        this.name = lectureRoom.getName();
        this.taInviteCode = lectureRoom.getTaInviteCode();
        this.stInviteCode = lectureRoom.getStInviteCode();
        this.onOff = lectureRoom.isOnOff();
    }

    public LectureRoomResponse(LectureRoomDto lectureRoomDto){
        this.lectureRoomId = lectureRoomDto.getLectureRoomId();
        this.name = lectureRoomDto.getName();
        this.taInviteCode = lectureRoomDto.getTaInviteCode();
        this.stInviteCode = lectureRoomDto.getStInviteCode();
        this.onOff = lectureRoomDto.isOnOff();
    }
}
