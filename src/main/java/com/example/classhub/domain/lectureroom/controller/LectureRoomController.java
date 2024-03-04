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


    @GetMapping("/lecture-room/updateForm/{lectureRoomId}")
    public String updateForm(@PathVariable Long lectureRoomId, Model model) {
        LectureRoomDto lectureRoomDto = lectureRoomService.findByRoomId(lectureRoomId);
        model.addAttribute("lectureRoom", lectureRoomDto);
        return "lectureRoomUpdate";
    }
    @PostMapping("/lecture-room/update/{lectureRoomId}")
    public String update(@PathVariable Long lectureRoomId, @ModelAttribute("lectureRoom") LectureRoomUpdateRequest request){
        lectureRoomService.update(lectureRoomId, LectureRoomDto.from(request));
        return "redirect:/lecture-room";
    }

    @GetMapping("/lecture-room/delete/{lectureRoomId}")
    public String delete(@PathVariable(value = "lectureRoomId") Long lectureRoomId){
        lectureRoomService.delete(lectureRoomId);
        return "redirect:/lecture-room";
    }
}
