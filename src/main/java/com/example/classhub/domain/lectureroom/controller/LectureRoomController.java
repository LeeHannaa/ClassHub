package com.example.classhub.domain.lectureroom.controller;

import com.example.classhub.domain.lectureroom.controller.request.LectureRoomCreateRequest;
import com.example.classhub.domain.lectureroom.controller.response.LectureRoomCreateResponse;
import com.example.classhub.domain.lectureroom.dto.LectureRoomDto;
import com.example.classhub.domain.lectureroom.service.LectureRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/lecture-room")
public class LectureRoomController {
    private final LectureRoomService lectureRoomService;

    @PostMapping("/create")
    public ResponseEntity<LectureRoomCreateResponse> createLectureRoom(@RequestBody LectureRoomCreateRequest request){
        LectureRoomDto lectureRoomDto = lectureRoomService.createLectureRoom(LectureRoomDto.from(request));
        LectureRoomCreateResponse lectureRoomCreateResponse = new LectureRoomCreateResponse(lectureRoomDto);
        return ResponseEntity.ok(lectureRoomCreateResponse);
    }

}
