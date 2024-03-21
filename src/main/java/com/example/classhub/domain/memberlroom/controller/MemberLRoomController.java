package com.example.classhub.domain.memberlroom.controller;

import com.example.classhub.domain.memberlroom.MemberLRoom;
import com.example.classhub.domain.memberlroom.dto.Permission;
import com.example.classhub.domain.memberlroom.dto.Role;
import com.example.classhub.domain.memberlroom.service.MemberLRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/memberlroom")
public class MemberLRoomController {
  private final MemberLRoomService memberLRoomService;

  @Autowired
  public MemberLRoomController(MemberLRoomService memberLRoomService) {
    this.memberLRoomService = memberLRoomService;
  }

  // Create
  @GetMapping("/new")
  public String showCreateForm(Model model) {
    model.addAttribute("memberLRoom", new MemberLRoom());
    model.addAttribute("allRoles", Role.values());
    model.addAttribute("allPermissions", Permission.values());
    return "memberLRoom/create-memberlroom";
  }

  @PostMapping
  public String createMemberLRoom(@ModelAttribute MemberLRoom memberLRoom) {
    memberLRoomService.createMemberLRoom(memberLRoom);
    return "redirect:/memberlroom";
  }

  // Read All
  @GetMapping
  public String listMemberLRooms(Model model) {
    model.addAttribute("memberLRooms", memberLRoomService.findAllMemberLRooms());
    return "memberLRoom/memberlrooms";
  }

  // Read One
  @GetMapping("/{id}")
  public String showMemberLRoomById(@PathVariable Long id, Model model) {
    MemberLRoom memberLRoom = memberLRoomService.findMemberLRoomById(id)
      .orElseThrow(() -> new IllegalArgumentException("Invalid memberLRoom Id:" + id));
    model.addAttribute("memberLRoom", memberLRoom);
    return "show-memberlroom";
  }

  // Update
  @GetMapping("/edit/{id}")
  public String showUpdateForm(@PathVariable Long id, Model model) {
    MemberLRoom memberLRoom = memberLRoomService.findMemberLRoomById(id)
      .orElseThrow(() -> new IllegalArgumentException("Invalid memberLRoom Id:" + id));
    model.addAttribute("memberLRoom", memberLRoom);
    model.addAttribute("allRoles", Role.values());
    model.addAttribute("allPermissions", Permission.values());
    return "memberLRoom/update-memberlroom";
  }

  @PostMapping("/update/{id}")
  public String updateMemberLRoom(@PathVariable Long id, @ModelAttribute MemberLRoom memberLRoom) {
    memberLRoomService.updateMemberLRoom(id, memberLRoom);
    return "redirect:/memberlroom";
  }

  // Delete
  @GetMapping("/delete/{id}")
  public String deleteMemberLRoom(@PathVariable Long id) {
    memberLRoomService.deleteMemberLRoom(id);
    return "redirect:/memberlroom";
  }
}
