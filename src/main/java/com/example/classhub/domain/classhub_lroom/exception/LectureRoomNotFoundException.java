package com.example.classhub.domain.classhub_lroom.exception;

public class LectureRoomNotFoundException extends RuntimeException{
    public LectureRoomNotFoundException() {
        super("존재하지 않는 강의실입니다.");
    }
}
