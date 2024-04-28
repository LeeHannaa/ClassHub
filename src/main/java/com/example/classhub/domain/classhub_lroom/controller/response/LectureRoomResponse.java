package com.example.classhub.domain.classhub_lroom.controller.response;

import com.example.classhub.domain.classhub_lroom.ClassHub_LRoom;
import com.example.classhub.domain.classhub_lroom.dto.LectureRoomDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureRoomResponse {
    private Long lectureRoomId;
    private String roomName;
    private String taInviteCode;
    private String stInviteCode;
    private String description;
    private boolean onOff;
    private String studentInfoKey;

    public LectureRoomResponse(ClassHub_LRoom lectureRoom){
        this.lectureRoomId = lectureRoom.getLRoomId();
        this.roomName = lectureRoom.getRoomName();
        this.taInviteCode = lectureRoom.getTaInviteCode();
        this.stInviteCode = lectureRoom.getStInviteCode();
        this.onOff = lectureRoom.isOnOff();
        this.description = lectureRoom.getDescription();
        this.studentInfoKey = lectureRoom.getStudentInfoKey();
    }

    public LectureRoomResponse(LectureRoomDto lectureRoomDto){
        this.lectureRoomId = lectureRoomDto.getLectureRoomId();
        this.roomName = lectureRoomDto.getRoomName();
        this.taInviteCode = lectureRoomDto.getTaInviteCode();
        this.stInviteCode = lectureRoomDto.getStInviteCode();
        this.onOff = lectureRoomDto.isOnOff();
        this.description = lectureRoomDto.getDescription();
    }
}
