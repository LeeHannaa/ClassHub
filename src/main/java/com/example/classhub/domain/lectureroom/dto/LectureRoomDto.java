package com.example.classhub.domain.lectureroom.dto;

import com.example.classhub.domain.lectureroom.LectureRoom;
import com.example.classhub.domain.lectureroom.controller.request.LectureRoomCreateRequest;
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
    private String name;
    private String taInviteCode;
    private String stInviteCode;
    private boolean onOff;

    public static LectureRoomDto from(LectureRoomCreateRequest request) {
        return LectureRoomDto.builder()
                .name(request.getName())
                .onOff(request.isOnOff())
                .build();
    }

    public static LectureRoomDto from(LectureRoom lectureRoom){
        return LectureRoomDto.builder()
                .lectureRoomId(lectureRoom.getLectureRoomId())
                .name(lectureRoom.getName())
                .taInviteCode(lectureRoom.getTaInviteCode())
                .stInviteCode(lectureRoom.getStInviteCode())
                .onOff(lectureRoom.isOnOff())
                .build();
    }
}
