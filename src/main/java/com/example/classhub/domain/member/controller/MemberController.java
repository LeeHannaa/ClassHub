package com.example.classhub.domain.member.controller;

import com.example.classhub.domain.classhub_lroom.dto.LectureRoomDto;
import com.example.classhub.domain.classhub_lroom.service.LectureRoomService;
import com.example.classhub.domain.member.controller.request.MemberCreateRequest;
import com.example.classhub.domain.member.controller.response.MemberListResponse;
import com.example.classhub.domain.member.dto.MemberDto;
import com.example.classhub.domain.member.service.MemberService;
import com.example.classhub.domain.tag.controller.response.TagListResponse;
import com.example.classhub.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class MemberController {
  private final MemberService memberService;
  private final LectureRoomService lectureRoomService;
  private final TagService tagService;

  @GetMapping ("/lecture-room/member/info/{lectureRoomId}")//member list 보여주기
  public String findMemberList(@PathVariable Long lectureRoomId, Model model){
    MemberListResponse memberListResponse = memberService.getMemberList();
    model.addAttribute("members", memberListResponse.getMembers());

    LectureRoomDto lectureRoomDto = lectureRoomService.findByRoomId(lectureRoomId);
    TagListResponse tagListResponse = tagService.getTagListByLectureId(lectureRoomId);
    model.addAttribute("lectureRoom", lectureRoomDto);
    model.addAttribute("tags", tagListResponse.getTags());
    return "/member/memberList";
  }

  @GetMapping("/memberForm") // member form 보여주기
  public String createMemberForm(Model model){
    model.addAttribute("member", new MemberCreateRequest());
    return "/member/memberForm";
  }

  @PostMapping("/saveMember") // member 저장하기
  public String createMember(@ModelAttribute("member") MemberCreateRequest request, RedirectAttributes redirectAttrs){
    try {
      memberService.createMember(MemberDto.from(request));
    } catch (IllegalArgumentException e) {
      redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
      return "redirect:/member/memberForm";
    }
    return "redirect:/member";
  }

  @GetMapping("/delete/{memberId}") // member 삭제하기
  public String deleteMember(@ModelAttribute("memberId") Long memberId){
    memberService.delete(memberId);
    return "redirect:/member";
  }

  @GetMapping("/updateForm/{memberId}") // member 수정하기
  public String updateForm(@ModelAttribute("memberId") Long memberId, Model model){
    MemberDto memberDto = memberService.findByMemberId(memberId);
    model.addAttribute("member", memberDto);
    return "/member/memberUpdate";
  }

  @PostMapping("/update/{memberId}") // member 수정하기
  public String update(@ModelAttribute("memberId") Long memberId, @ModelAttribute("member") MemberCreateRequest request){
    memberService.update(memberId, MemberDto.from(request));
    return "redirect:/member";
  }

}
