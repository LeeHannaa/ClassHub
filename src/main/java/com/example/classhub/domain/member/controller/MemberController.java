package com.example.classhub.domain.member.controller;

import com.example.classhub.domain.member.controller.request.MemberCreateRequest;
import com.example.classhub.domain.member.controller.response.MemberListResponse;
import com.example.classhub.domain.member.dto.MemberDto;
import com.example.classhub.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/memberList") //member list 보여주기
    public String findMemberList(Model model){
        MemberListResponse memberListResponse = memberService.getMemberList();
        model.addAttribute("members", memberListResponse.getMembers());
        return "memberList";
    }

    @GetMapping("/member/memberForm") // member form 보여주기
    public String createMemberForm(Model model){
        model.addAttribute("member", new MemberCreateRequest());
        return "memberForm";
    }

    @PostMapping("/member/saveMember") // member 저장하기
    public String createMember(@ModelAttribute("member") MemberCreateRequest request, RedirectAttributes redirectAttrs){
        try {
            memberService.createMember(MemberDto.from(request));
        } catch (IllegalArgumentException e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/member/memberForm";
        }
        return "redirect:/member/memberList";
    }


}
