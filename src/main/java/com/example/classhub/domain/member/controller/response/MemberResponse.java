package com.example.classhub.domain.member.controller.response;

import com.example.classhub.domain.member.ClassHub_Member;
import com.example.classhub.domain.member.dto.MemberDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class MemberResponse {
    private Long memberId;
    private String member_name;
    private String email;
    private String uniqueId;

    public MemberResponse(ClassHub_Member classHubMember) {
        this.memberId = classHubMember.getMemberId();
        this.member_name = classHubMember.getMember_name();
        this.email = classHubMember.getEmail();
        this.uniqueId = classHubMember.getUniqueId();
    }

    public MemberResponse(MemberDto memberDto) {
        this.memberId = memberDto.getMemberId();
        this.member_name = memberDto.getMember_name();
        this.email = memberDto.getEmail();
        this.uniqueId = memberDto.getUniqueId();
    }
}
