package com.example.classhub.domain.lectureroom.controller;

import com.example.classhub.domain.lectureroom.controller.request.LectureRoomCreateRequest;
import com.example.classhub.domain.lectureroom.controller.request.LectureRoomUpdateRequest;
import com.example.classhub.domain.lectureroom.controller.response.LectureRoomListResponse;
import com.example.classhub.domain.lectureroom.controller.response.LectureRoomUpdateResponse;
import com.example.classhub.domain.lectureroom.dto.LectureRoomDto;
import com.example.classhub.domain.lectureroom.service.LectureRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@CrossOrigin("*")
public class LectureRoomController {
    private final LectureRoomService lectureRoomService;

    @GetMapping("/lecture-room/lectureRoomForm")
    public String createLectureRoomFrom(Model model){
        model.addAttribute("lectureRoom", new LectureRoomCreateRequest());
        return "lectureRoomForm";
    }
    @PostMapping("/lecture-room/saveLecture")
    public String createLectureRoom(@ModelAttribute("lectureRoom") LectureRoomCreateRequest request){
        lectureRoomService.createLectureRoom(LectureRoomDto.from(request));
        return "redirect:/lecture-room";
    }

    @GetMapping("/lecture-room")
    public String findLectureRoomList(Model model){
        LectureRoomListResponse lectureRoomListResponse = lectureRoomService.getLectureRoomList();
        model.addAttribute("lectureRooms", lectureRoomListResponse.getLectureRooms());
        return "index";
    }


    @PatchMapping("/lecture-room/{lectureRoomId}")
    public ResponseEntity<LectureRoomUpdateResponse> update(@PathVariable Long lectureRoomId, @RequestBody LectureRoomUpdateRequest request){
        LectureRoomDto lectureRoomDto = lectureRoomService.update(lectureRoomId, LectureRoomDto.from(request));
        LectureRoomUpdateResponse lectureRoomUpdateResponse = new LectureRoomUpdateResponse(lectureRoomDto);
        return ResponseEntity.ok(lectureRoomUpdateResponse);
    }

    @DeleteMapping("/lecture-room/{lectureRoomId}")
    public void delete(@PathVariable Long lectureRoomId){
        lectureRoomService.delete(lectureRoomId);
    }
}
