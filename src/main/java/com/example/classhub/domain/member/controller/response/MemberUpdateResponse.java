package com.example.classhub.domain.member.controller.response;

import com.example.classhub.domain.member.dto.MemberDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class MemberUpdateResponse {
    private Long memberId;
    private String member_name;
    private String email;
    private String unique_id;

    public MemberUpdateResponse(MemberDto memberDto) {
        this.memberId = memberDto.getMemberId();
        this.member_name = memberDto.getMember_name();
        this.email = memberDto.getEmail();
        this.unique_id = memberDto.getUnique_id();
    }
}
