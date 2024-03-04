package com.example.classhub.domain.lectureroom.controller.response;

import com.example.classhub.domain.lectureroom.dto.LectureRoomDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class LectureRoomUpdateResponse {
    private String name;
    private String taInviteCode;
    private String stInviteCode;
    private boolean onOff;

    public LectureRoomUpdateResponse(LectureRoomDto lectureRoomDto) {
        this.name = lectureRoomDto.getName();
        this.taInviteCode = lectureRoomDto.getTaInviteCode();
        this.stInviteCode = lectureRoomDto.getStInviteCode();
        this.onOff = lectureRoomDto.isOnOff();
    }
}
