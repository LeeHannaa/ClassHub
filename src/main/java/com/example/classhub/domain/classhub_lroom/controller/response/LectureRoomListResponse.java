package com.example.classhub.domain.classhub_lroom.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class LectureRoomListResponse {
    private List<LectureRoomResponse> lectureRooms;

    public LectureRoomListResponse(List<LectureRoomResponse> lectureRooms){
        this.lectureRooms = lectureRooms;
    }
}
