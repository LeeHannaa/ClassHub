package com.example.classhub.domain.lectureroom.controller;

import com.example.classhub.domain.lectureroom.controller.request.LectureRoomCreateRequest;
import com.example.classhub.domain.lectureroom.controller.request.LectureRoomUpdateRequest;
import com.example.classhub.domain.lectureroom.controller.response.LectureRoomListResponse;
import com.example.classhub.domain.lectureroom.controller.response.LectureRoomResponse;
import com.example.classhub.domain.lectureroom.dto.LectureRoomDto;
import com.example.classhub.domain.lectureroom.service.LectureRoomService;
import com.example.classhub.domain.tag.controller.response.TagListResponse;
import com.example.classhub.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin("*")
public class LectureRoomController {
    private final LectureRoomService lectureRoomService;
    private final TagService tagService;

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
    public String findLectureRoomList(Model model, @RequestParam(name = "searchKeyword", required = false, defaultValue = "")String searchKeyword){
        LectureRoomListResponse lectureRoomListResponse = null;
        TagListResponse tagListResponse = tagService.getTagList();

        if (searchKeyword.isEmpty()){
            lectureRoomListResponse = lectureRoomService.getLectureRoomList();
        }
        else{
            lectureRoomListResponse = lectureRoomService.findByKeyword(searchKeyword);
        }
        model.addAttribute("lectureRooms", lectureRoomListResponse.getLectureRooms());
        model.addAttribute("tags", tagListResponse.getTags());

        return "index";
    }
    @GetMapping("/lecture-room/detail/{lectureRoomId}")
    public String findLectureRoomDetail(@PathVariable Long lectureRoomId, Model model) {
        LectureRoomDto lectureRoomDto = lectureRoomService.findByRoomId(lectureRoomId);
        model.addAttribute("lectureRoom", lectureRoomDto);
        return "lectureRoomDetail";
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
        //Todo: tag 매핑 후 삭제 기능 수정
        tagService.tagDelete(lectureRoomId);
        return "redirect:/lecture-room";
    }
}
