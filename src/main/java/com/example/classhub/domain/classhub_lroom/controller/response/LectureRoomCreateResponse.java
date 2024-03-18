package com.example.classhub.domain.classhub_lroom.controller.response;

import com.example.classhub.domain.classhub_lroom.dto.LectureRoomDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class LectureRoomCreateResponse {
    private String roomName;
    private String taInviteCode;
    private String stInviteCode;
    private String description;
    private boolean onOff;

    public LectureRoomCreateResponse(LectureRoomDto lectureRoomDto) {
        this.roomName = lectureRoomDto.getRoomName();
        this.taInviteCode = lectureRoomDto.getTaInviteCode();
        this.stInviteCode = lectureRoomDto.getStInviteCode();
        this.description = lectureRoomDto.getDescription();
        this.onOff = lectureRoomDto.isOnOff();
    }
}
