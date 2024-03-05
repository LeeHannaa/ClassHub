package com.example.classhub.domain.member.controller.response;

import com.example.classhub.domain.member.Member;
import com.example.classhub.domain.member.dto.MemberDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class MemberResponse {
    private Long memberId;
    private String member_name;
    private String email;
    private String unique_id;

    public MemberResponse(Member member) {
        this.memberId = member.getMemberId();
        this.member_name = member.getMember_name();
        this.email = member.getEmail();
        this.unique_id = member.getUnique_id();
    }

    public MemberResponse(MemberDto memberDto) {
        this.memberId = memberDto.getMemberId();
        this.member_name = memberDto.getMember_name();
        this.email = memberDto.getEmail();
        this.unique_id = memberDto.getUnique_id();
    }
}
