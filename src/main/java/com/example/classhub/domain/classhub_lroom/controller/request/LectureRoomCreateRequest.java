package com.example.classhub.domain.classhub_lroom.controller.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class LectureRoomCreateRequest {
    private String roomName;
    private String description;
    private boolean OnOff;
}
