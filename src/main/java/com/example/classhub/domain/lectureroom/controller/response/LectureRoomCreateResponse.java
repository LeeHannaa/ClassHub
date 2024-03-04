package com.example.classhub.domain.lectureroom.controller.response;

import com.example.classhub.domain.lectureroom.dto.LectureRoomDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class LectureRoomCreateResponse {
    private String name;
    private String taInviteCode;
    private String stInviteCode;
    private boolean onOff;

    public LectureRoomCreateResponse(LectureRoomDto lectureRoomDto) {
        this.name = lectureRoomDto.getName();
        this.taInviteCode = lectureRoomDto.getTaInviteCode();
        this.stInviteCode = lectureRoomDto.getStInviteCode();
        this.onOff = lectureRoomDto.isOnOff();
    }
}
