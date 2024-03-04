package com.example.classhub.domain.lectureroom.controller;

import com.example.classhub.domain.lectureroom.controller.request.LectureRoomCreateRequest;
import com.example.classhub.domain.lectureroom.controller.request.LectureRoomUpdateRequest;
import com.example.classhub.domain.lectureroom.controller.response.LectureRoomCreateResponse;
import com.example.classhub.domain.lectureroom.controller.response.LectureRoomListResponse;
import com.example.classhub.domain.lectureroom.controller.response.LectureRoomUpdateResponse;
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

    @GetMapping("/list")
    public ResponseEntity<LectureRoomListResponse> findLectureRoomList(){
        LectureRoomListResponse lectureRoomListResponse = lectureRoomService.getLectureRoomList();
        return ResponseEntity.ok(lectureRoomListResponse);
    }

    @PatchMapping("/{lectureRoomId}")
    public ResponseEntity<LectureRoomUpdateResponse> update(@PathVariable Long lectureRoomId, @RequestBody LectureRoomUpdateRequest request){
        LectureRoomDto lectureRoomDto = lectureRoomService.update(lectureRoomId, LectureRoomDto.from(request));
        LectureRoomUpdateResponse lectureRoomUpdateResponse = new LectureRoomUpdateResponse(lectureRoomDto);
        return ResponseEntity.ok(lectureRoomUpdateResponse);
    }

}
