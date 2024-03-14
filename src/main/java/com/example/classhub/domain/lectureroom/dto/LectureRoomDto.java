package com.example.classhub.domain.lectureroom.dto;

import com.example.classhub.domain.lectureroom.LectureRoom;
import com.example.classhub.domain.lectureroom.controller.request.LectureRoomCreateRequest;
import com.example.classhub.domain.lectureroom.controller.request.LectureRoomUpdateRequest;
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
    private String l_room_name;
    private String taInviteCode;
    private String stInviteCode;
    private boolean onOff;
    private String description;

    public static LectureRoomDto from(LectureRoomCreateRequest request) {
        return LectureRoomDto.builder()
                .l_room_name(request.getL_room_name())
                .onOff(request.isOnOff())
                .description(request.getDescription())
                .build();
    }
    public static LectureRoomDto from(LectureRoomUpdateRequest request) {
        return LectureRoomDto.builder()
                .l_room_name(request.getL_room_name())
                .onOff(request.isOnOff())
                .description(request.getDescription())
                .build();
    }

    public static LectureRoomDto from(LectureRoom lectureRoom){
        return LectureRoomDto.builder()
                .lectureRoomId(lectureRoom.getLectureRoomId())
                .l_room_name(lectureRoom.getL_room_name())
                .taInviteCode(lectureRoom.getTaInviteCode())
                .stInviteCode(lectureRoom.getStInviteCode())
                .onOff(lectureRoom.isOnOff())
                .description(lectureRoom.getDescription())
                .build();
    }
}
