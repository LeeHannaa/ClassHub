package com.example.classhub.domain.classhub_lroom.controller.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class LectureRoomUpdateRequest {
    private String roomName;
    private boolean OnOff;
    private String description;
    private String taInviteCode;
    private String stInviteCode;
    private String studentInfo;
}
