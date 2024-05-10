package com.example.classhub.domain.memberlroom.controller;

import com.example.classhub.domain.classhub_lroom.dto.LectureRoomDto;
import com.example.classhub.domain.classhub_lroom.service.LectureRoomService;
import com.example.classhub.domain.member.ClassHub_Member;
import com.example.classhub.domain.memberlroom.ClassHub_MemberLRoom;
import com.example.classhub.domain.memberlroom.dto.Permission;
import com.example.classhub.domain.memberlroom.dto.Role;
import com.example.classhub.domain.memberlroom.service.MemberLRoomService;
import com.example.classhub.domain.tag.controller.response.TagListResponse;
import com.example.classhub.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

  @PostMapping("/memberlroom/create/one/{lectureRoomId}")
  public String createMemberByOne(@PathVariable Long lectureRoomId, @ModelAttribute ClassHub_MemberLRoom classHubMemberLRoom, @ModelAttribute ClassHub_Member classHubMember) {
    memberLRoomService.createMemberByOne(lectureRoomId, classHubMemberLRoom, classHubMember);
    return "redirect:/memberList";
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
    LectureRoomDto lectureRoomDto = lectureRoomService.findByRoomId(lectureRoomId);
    model.addAttribute("lectureRoom", lectureRoomDto);
    model.addAttribute("memberLRooms", memberLRoomService.findMembersByLRoomId(lectureRoomId));
    TagListResponse tagListResponse = tagService.getTagListByLectureId(lectureRoomId);
    model.addAttribute("tags", tagListResponse.getTags());
    return "member/memberList";
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
  @GetMapping("/memberlroom/delete/{id}")
  public String deleteMemberLRoom(@PathVariable Long id) {
    memberLRoomService.deleteMemberLRoom(id);
    return "redirect:/memberlroom";
  }
}
