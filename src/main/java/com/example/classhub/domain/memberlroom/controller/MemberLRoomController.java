package com.example.classhub.domain.memberlroom.controller;

import com.example.classhub.domain.classhub_lroom.ClassHub_LRoom;
import com.example.classhub.domain.classhub_lroom.controller.request.LectureRoomCreateRequest;
import com.example.classhub.domain.classhub_lroom.dto.LectureRoomDto;
import com.example.classhub.domain.classhub_lroom.service.LectureRoomService;
import com.example.classhub.domain.member.ClassHub_Member;
import com.example.classhub.domain.member.dto.MemberDto;
import com.example.classhub.domain.memberlroom.ClassHub_MemberLRoom;
import com.example.classhub.domain.memberlroom.controller.request.MemberLRoomMemberCreateRequest;
import com.example.classhub.domain.memberlroom.dto.MemberLRoomDto;
import com.example.classhub.domain.memberlroom.dto.Permission;
import com.example.classhub.domain.memberlroom.dto.Role;
import com.example.classhub.domain.memberlroom.service.MemberLRoomService;
import com.example.classhub.domain.tag.controller.response.TagListResponse;
import com.example.classhub.domain.tag.service.TagService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberLRoomController {
  private final MemberLRoomService memberLRoomService;
  private final TagService tagService;
  private final LectureRoomService lectureRoomService;

  // Create
  @GetMapping("/memberlroom/new")
  public String showCreateForm(Model model) {
    model.addAttribute("memberLRoom", new ClassHub_MemberLRoom());
    model.addAttribute("allRoles", Role.values());
    model.addAttribute("allPermissions", Permission.values());
    return "memberLRoom/create-memberlroom";
  }

  @PostMapping("/memberlroom")
  public String createMemberLRoom(@ModelAttribute ClassHub_MemberLRoom classHubMemberLRoom) {
    memberLRoomService.createMemberLRoom(classHubMemberLRoom);
    return "redirect:/memberlroom";
  }

  // Read All
  @GetMapping("/memberlroom")
  public String listMemberLRooms(Model model) {
    model.addAttribute("memberLRooms", memberLRoomService.findAllMemberLRooms());
    return "memberLRoom/memberlrooms";
  }

  // 해당 강의실에 입장한 멤버만 불러오기
  @GetMapping("/lecture-room/member/info/{lectureRoomId}")
  public String listMemberLRooms(@PathVariable Long lectureRoomId, Model model,
                                 @RequestParam(value = "page", defaultValue = "0") int page) {
    LectureRoomDto lectureRoomDto = lectureRoomService.findByRoomId(lectureRoomId);
    model.addAttribute("lectureRoom", lectureRoomDto);

    Page<ClassHub_MemberLRoom> classHub_memberLRoomPageList = memberLRoomService.findMembersByLRoomId(lectureRoomId, page);
    model.addAttribute("memberLRooms", classHub_memberLRoomPageList);
    model.addAttribute("maxPage", 5);

    TagListResponse tagListResponse = tagService.getTagListByLectureId(lectureRoomId);
    model.addAttribute("tags", tagListResponse.getTags());
//    model.addAttribute("classHubMemberLRoom", new ClassHub_MemberLRoom());
    return "member/memberList";
  }

  @PostMapping("/memberlroom/create/one/{lectureRoomId}")
  public ResponseEntity<String> createMemberByOne(@PathVariable Long lectureRoomId, @RequestBody MemberLRoomMemberCreateRequest memberLRoomMemberCreateRequest) {
    LectureRoomDto lectureRoomDto = lectureRoomService.findByRoomId(lectureRoomId);
    MemberDto memberDto = MemberDto.from(memberLRoomMemberCreateRequest);
    // 강의실 정보 + 입력받은 새 멤버의 정보를 이용해서 멤버강의실에서 처리하겠다.
    Boolean createSuccess = memberLRoomService.createMemberByOne(lectureRoomDto, memberDto);
    return createSuccess ? ResponseEntity.ok().body("Success") : ResponseEntity.ok().body("Failure");
  }

  // Read One
  @GetMapping("/memberlroom/{id}")
  public String showMemberLRoomById(@PathVariable Long id, Model model) {
    ClassHub_MemberLRoom classHubMemberLRoom = memberLRoomService.findMemberLRoomById(id)
      .orElseThrow(() -> new IllegalArgumentException("Invalid memberLRoom Id:" + id));
    model.addAttribute("memberLRoom", classHubMemberLRoom);
    return "show-memberlroom";
  }

  // Update
  @GetMapping("/memberlroom/edit/{id}")
  public String showUpdateForm(@PathVariable Long id, Model model) {
    ClassHub_MemberLRoom classHubMemberLRoom = memberLRoomService.findMemberLRoomById(id)
      .orElseThrow(() -> new IllegalArgumentException("Invalid memberLRoom Id:" + id));
    model.addAttribute("memberLRoom", classHubMemberLRoom);
    model.addAttribute("allRoles", Role.values());
    model.addAttribute("allPermissions", Permission.values());
    return "memberLRoom/update-memberlroom";
  }

  @PostMapping("/memberlroom/update/role/{id}")
  public ResponseEntity<String> updateMemberLRoomRole(@PathVariable Long id, @RequestBody ClassHub_MemberLRoom classHubMemberLRoom) {
    classHubMemberLRoom.setRole(Role.from(classHubMemberLRoom.getRole().getRole()));
    memberLRoomService.updateMemberLRoomRole(id, classHubMemberLRoom);
    return ResponseEntity.ok().body("MemberLRoom Role updated successfully.");
  }

  @PostMapping("/memberlroom/update/permission/{id}")
  public ResponseEntity<String> updateMemberLRoomPermission(@PathVariable Long id, @RequestBody ClassHub_MemberLRoom classHubMemberLRoom) {
    memberLRoomService.updateMemberLRoomPermission(id, classHubMemberLRoom);
    return ResponseEntity.ok().body("MemberLRoom Permission updated successfully.");
  }
  // Delete
  @PostMapping("/memberlroom/delete/{lectureRoomId}/{uniqueId}")
  public String deleteMemberLRoom(@PathVariable Long lectureRoomId, @PathVariable String uniqueId) {
    Long id = memberLRoomService.findMemberIdForDelete(lectureRoomId, uniqueId);
    memberLRoomService.deleteMemberLRoom(id);
    return "redirect:/lecture-room";
  }
}
