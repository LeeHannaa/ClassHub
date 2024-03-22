package com.example.classhub.domain.member.controller.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class MemberCreateRequest {
    private Long memberId;
    private String member_name;
    private String email;
    private String uniqueId;
    private String nickname;
    private int login_count;
}
