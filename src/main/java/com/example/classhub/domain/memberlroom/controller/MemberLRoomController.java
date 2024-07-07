package com.example.classhub.domain.memberlroom.controller;

import com.example.classhub.domain.classhub_lroom.dto.LectureRoomDto;
import com.example.classhub.domain.classhub_lroom.service.LectureRoomService;
import com.example.classhub.domain.member.ClassHub_Member;
import com.example.classhub.domain.member.dto.MemberDto;
import com.example.classhub.domain.memberlroom.ClassHub_MemberLRoom;
import com.example.classhub.domain.memberlroom.controller.request.MemberLRoomMemberCreateRequest;
import com.example.classhub.domain.memberlroom.dto.Permission;
import com.example.classhub.domain.memberlroom.dto.Role;
import com.example.classhub.domain.memberlroom.service.MemberLRoomService;
import com.example.classhub.domain.tag.controller.response.TagListResponse;
import com.example.classhub.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

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
  public String listMemberLRooms(@PathVariable Long lectureRoomId, Model model) {
    LectureRoomDto lectureRoomDto = lectureRoomService.findLRoomDtoByRoomId(lectureRoomId);
    model.addAttribute("lectureRoom", lectureRoomDto);

    List<ClassHub_MemberLRoom> classHub_memberLRoomPageList = memberLRoomService.findMembersByLRoomId(lectureRoomId);
    model.addAttribute("memberLRooms", classHub_memberLRoomPageList);
    model.addAttribute("maxPage", 5);

    TagListResponse tagListResponse = tagService.getTagListByLectureId(lectureRoomId);
    model.addAttribute("tags", tagListResponse.getTags());
//    model.addAttribute("classHubMemberLRoom", new ClassHub_MemberLRoom());
    return "member/memberList";
  }


  @PostMapping("/memberlroom/create/one/{lectureRoomId}")
  public String createMemberByOne(@PathVariable Long lectureRoomId, @ModelAttribute MemberLRoomMemberCreateRequest memberLRoomMemberCreateRequest, RedirectAttributes redirectAttributes) {
    try {
      LectureRoomDto lectureRoomDto = lectureRoomService.findLRoomDtoByRoomId(lectureRoomId);
      MemberDto memberDto = MemberDto.from(memberLRoomMemberCreateRequest);
      Boolean success = memberLRoomService.createMemberByOne(lectureRoomDto, memberDto);
      if(success) redirectAttributes.addAttribute("createState", true);
      else redirectAttributes.addAttribute("failState", true);
    } catch (Exception e) {
      redirectAttributes.addAttribute("error", "추가 중 에러 발생");
    }
    return "redirect:/lecture-room/member/info/" + lectureRoomId;
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

  @PostMapping("/memberlroom/update/role")
  public String updateMemberLRoomRole(@RequestParam("id") Long id, @ModelAttribute ClassHub_MemberLRoom classHubMemberLRoom, RedirectAttributes redirectAttributes) {
    // 역할 업데이트 로직 구현
    Optional<ClassHub_MemberLRoom> classHub_memberLRoomOptional = memberLRoomService.findById(id);
    String lRoomId = null;
    if (classHub_memberLRoomOptional.isPresent()) {
      lRoomId = classHub_memberLRoomOptional.get().getLectureRoom().getLRoomId().toString();
      ClassHub_MemberLRoom existingMemberLRoom = classHub_memberLRoomOptional.get();
      existingMemberLRoom.setRole(classHubMemberLRoom.getRole());
      memberLRoomService.updateMemberLRoomRole(id, classHubMemberLRoom);
      redirectAttributes.addAttribute("status", true);
    } else {
      redirectAttributes.addAttribute("status", false);
    }
    return "redirect:/lecture-room/member/info/" + lRoomId;
  }

  @PostMapping("/memberlroom/update/permission")
  public String updateMemberLRoomPermission(@RequestParam("id") Long id, @ModelAttribute ClassHub_MemberLRoom classHubMemberLRoom, RedirectAttributes redirectAttributes) {
    // 역할 업데이트 로직 구현
    Optional<ClassHub_MemberLRoom> classHub_memberLRoomOptional = memberLRoomService.findById(id);
    String lRoomId = null;
    if (classHub_memberLRoomOptional.isPresent()) {
      lRoomId = classHub_memberLRoomOptional.get().getLectureRoom().getLRoomId().toString();
      ClassHub_MemberLRoom existingMemberLRoom = classHub_memberLRoomOptional.get();
      existingMemberLRoom.setPermission(classHubMemberLRoom.getPermission());
      memberLRoomService.updateMemberLRoomPermission(id, classHubMemberLRoom);
      redirectAttributes.addAttribute("status", true);
    } else {
      redirectAttributes.addAttribute("status", false);
    }
    return "redirect:/lecture-room/member/info/" + lRoomId;
  }
  // Delete
  @PostMapping("/memberlroom/delete/{lectureRoomId}/{uniqueId}")
  public String deleteMemberLRoom(@PathVariable Long lectureRoomId, @PathVariable String uniqueId) {
    System.out.println("멤버 삭제 확인" + lectureRoomId + uniqueId);
    Long id = memberLRoomService.findMemberIdForDelete(lectureRoomId, uniqueId);
    memberLRoomService.deleteMemberLRoom(id);
    return "redirect:/lecture-room/member/info/" + lectureRoomId;
  }
}
