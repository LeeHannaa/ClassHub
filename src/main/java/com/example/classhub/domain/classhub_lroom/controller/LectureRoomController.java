package com.example.classhub.domain.classhub_lroom.controller;

import com.example.classhub.domain.classhub_lroom.controller.request.LectureRoomCreateRequest;
import com.example.classhub.domain.classhub_lroom.controller.request.LectureRoomUpdateRequest;
import com.example.classhub.domain.classhub_lroom.controller.response.LectureRoomListResponse;
import com.example.classhub.domain.classhub_lroom.dto.LectureRoomDto;
import com.example.classhub.domain.classhub_lroom.service.LectureRoomService;
import com.example.classhub.domain.tag.controller.response.TagListResponse;
import com.example.classhub.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@CrossOrigin("*")
public class LectureRoomController {
    private final LectureRoomService lectureRoomService;
    private final TagService tagService;

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

        // 강의실 생성 폼을 위한 모델
        model.addAttribute("lectureRoomCreateRequest", new LectureRoomCreateRequest());

        return "index";
    }

    @PostMapping("/lecture-room/saveLecture")
    public String createLectureRoom(@ModelAttribute("lectureRoom") LectureRoomCreateRequest request){
        lectureRoomService.createLectureRoom(LectureRoomDto.from(request));
        return "redirect:/lecture-room";
    }


    @GetMapping("/lecture-room/detail/{lectureRoomId}")
    public String findLectureRoomDetail(@PathVariable Long lectureRoomId, Model model) {
        LectureRoomDto lectureRoomDto = lectureRoomService.findByRoomId(lectureRoomId);
        TagListResponse tagListResponse = tagService.getTagListByLectureId(lectureRoomId);
        model.addAttribute("lectureRoom", lectureRoomDto);
        model.addAttribute("tags", tagListResponse.getTags());
        return "lectureRoomDetail";
    }
    @GetMapping("/lecture-room/detail/info/{lectureRoomId}")
    public String findLectureRoomDetailInfo(@PathVariable Long lectureRoomId, Model model) {
        LectureRoomDto lectureRoomDto = lectureRoomService.findByRoomId(lectureRoomId);
        TagListResponse tagListResponse = tagService.getTagListByLectureId(lectureRoomId);
        model.addAttribute("lectureRoom", lectureRoomDto);
        model.addAttribute("tags", tagListResponse.getTags());
        return "lectureRoom/lectureRoomInfo";
    }
    @PostMapping("/lecture-room/update/{lectureRoomId}")
    public String update(@PathVariable Long lectureRoomId, @ModelAttribute("lectureRoom") LectureRoomUpdateRequest request, RedirectAttributes attributes){
        lectureRoomService.update(lectureRoomId, LectureRoomDto.from(request));
        attributes.addAttribute("success", true);
        return "redirect:/lecture-room/detail/info/" + lectureRoomId;
    }

    @PostMapping("/lecture-room/delete/{lectureRoomId}")
    public String delete(@PathVariable(value = "lectureRoomId") Long lectureRoomId){
        //Todo: tag 매핑 후 삭제 기능 수정
        lectureRoomService.delete(lectureRoomId);
        return "redirect:/lecture-room";
    }
}
