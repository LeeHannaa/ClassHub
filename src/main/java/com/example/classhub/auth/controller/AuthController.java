package com.example.classhub.auth.controller;

import com.example.classhub.auth.service.HisnetLoginService;
import com.example.classhub.domain.member.controller.request.LoginRequest;
import com.example.classhub.domain.member.dto.MemberDto;
import com.example.classhub.domain.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final HisnetLoginService hisnetLoginService;
    private final MemberService memberService;

    @GetMapping("/auth/login") // member form 보여주기
    public String createMemberForm(@RequestParam String token, Model model, HttpSession session) {
        if (token == null || token.isEmpty()) {
            System.out.println("토큰이 없습니다.");
            return "redirect:/error"; // 오류 페이지로 리디렉션
        }

        MemberDto memberDto = memberService.createMember(hisnetLoginService.callHisnetLoginApi(MemberDto.from(token)));
        model.addAttribute("member", memberDto);
        session.setAttribute("member", memberDto);

        return "redirect:/lecture-room";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/classhub";
    }
}