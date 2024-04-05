package com.example.classhub.domain.classhub_lroom.dto;

import com.example.classhub.domain.classhub_lroom.ClassHub_LRoom;
import com.example.classhub.domain.classhub_lroom.controller.request.LectureRoomCreateRequest;
import com.example.classhub.domain.classhub_lroom.controller.request.LectureRoomUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class LectureRoomDto {
    private Long lectureRoomId;
    private String roomName;
    private String taInviteCode;
    private String stInviteCode;
    private String description;
    private boolean onOff;

    public static LectureRoomDto from(LectureRoomCreateRequest request) {
        return LectureRoomDto.builder()
                .roomName(request.getRoomName())
                .description(request.getDescription())
                .onOff(request.isOnOff())
                .build();
    }
    public static LectureRoomDto from(LectureRoomUpdateRequest request) {
        return LectureRoomDto.builder()
                .roomName(request.getRoomName())
                .description(request.getDescription())
                .taInviteCode(request.getTaInviteCode())
                .stInviteCode(request.getStInviteCode())
                .onOff(request.isOnOff())
                .build();
    }

    public static LectureRoomDto from(ClassHub_LRoom lectureRoom){
        return LectureRoomDto.builder()
                .lectureRoomId(lectureRoom.getLRoomId())
                .roomName(lectureRoom.getRoomName())
                .description(lectureRoom.getDescription())
                .taInviteCode(lectureRoom.getTaInviteCode())
                .stInviteCode(lectureRoom.getStInviteCode())
                .onOff(lectureRoom.isOnOff())
                .build();
    }
}
