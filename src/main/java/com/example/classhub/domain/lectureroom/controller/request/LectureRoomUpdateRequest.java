package com.example.classhub.domain.lectureroom.controller.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class LectureRoomUpdateRequest {
    private String l_room_name;
    private boolean OnOff;
    private String description;
}
