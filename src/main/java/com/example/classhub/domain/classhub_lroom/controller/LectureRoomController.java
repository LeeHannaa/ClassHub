package com.example.classhub.domain.classhub_lroom.controller;

import com.example.classhub.domain.classhub_lroom.controller.request.LectureRoomCreateRequest;
import com.example.classhub.domain.classhub_lroom.controller.request.LectureRoomUpdateRequest;
import com.example.classhub.domain.classhub_lroom.controller.response.LectureRoomListResponse;
import com.example.classhub.domain.classhub_lroom.dto.LectureRoomDto;
import com.example.classhub.domain.classhub_lroom.service.LectureRoomService;
import com.example.classhub.domain.member.dto.MemberDto;
import com.example.classhub.domain.memberlroom.service.MemberLRoomService;
import com.example.classhub.domain.post.controller.response.PostListResponse;
import com.example.classhub.domain.post.service.PostService;
import com.example.classhub.domain.tag.controller.response.TagListResponse;
import com.example.classhub.domain.tag.service.TagService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LectureRoomController {
    private final LectureRoomService lectureRoomService;
    private final TagService tagService;
    private final PostService postService;
    private final MemberLRoomService memberLRoomService;

    @GetMapping("/lecture-room")
    public String findLectureRoomList(Model model, @RequestParam(name = "searchKeyword", required = false, defaultValue = "")String searchKeyword, HttpSession session){
        LectureRoomListResponse lectureRoomListResponse = null;
        TagListResponse tagListResponse = tagService.getTagList();

        MemberDto memberDto = (MemberDto) session.getAttribute("member");
        System.out.println("memberDto.getMemberId() = " + memberDto.getMemberId());
        if (searchKeyword.isEmpty()){
            lectureRoomListResponse = lectureRoomService.getLectureRoomList(memberDto.getMemberId());
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
    public String createLectureRoom(@ModelAttribute("lectureRoom") LectureRoomCreateRequest request, @RequestParam("studentFile") MultipartFile studentFile) {
        LectureRoomDto lectureRoomDto = lectureRoomService.createLectureRoom(LectureRoomDto.from(request));
        if(studentFile != null && !studentFile.isEmpty())
            memberLRoomService.createMemberLRoom(lectureRoomDto.getLectureRoomId(), studentFile);
        return "redirect:/lecture-room";
    }

    @GetMapping("/lecture-room/detail/{lectureRoomId}")
    public String findLectureRoomDetail(@PathVariable Long lectureRoomId, Model model,
                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "5") int size,
                                        HttpSession session) {
        LectureRoomDto lectureRoomDto = lectureRoomService.findByRoomId(lectureRoomId);
        MemberDto memberDto = (MemberDto) session.getAttribute("member");
        memberLRoomService.createMemberByOne(lectureRoomDto, memberDto);

        TagListResponse tagListResponse = tagService.getTagListByLectureId(lectureRoomId);
        PostListResponse postListResponse = postService.getPostListByLectureRoomId(lectureRoomId, page, size);

        model.addAttribute("posts", postListResponse.getPosts()); // 수정된 부분
        model.addAttribute("totalPages", postListResponse.getTotalPages());
        model.addAttribute("currentPage", postListResponse.getCurrentPage());
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
        lectureRoomService.delete(lectureRoomId);
        return "redirect:/lecture-room";
    }
}
