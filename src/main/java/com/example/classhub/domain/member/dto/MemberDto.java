package com.example.classhub.domain.member.dto;


import com.example.classhub.domain.member.ClassHub_Member;
import com.example.classhub.domain.member.controller.request.MemberCreateRequest;
import com.example.classhub.domain.member.controller.request.MemberUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MemberDto {
    private Long memberId;
    private String member_name;
    private String email;
    private String uniqueId;
    private String nickname;
    private int login_count;

    public static MemberDto from(MemberCreateRequest request){
        return MemberDto.builder()
                .memberId(request.getMemberId())
                .member_name(request.getMember_name())
                .email(request.getEmail())
                .uniqueId(request.getUniqueId())
                .nickname(request.getNickname())
                .login_count(request.getLogin_count())
                .build();
    }

    public static MemberDto from(MemberUpdateRequest request){
        return MemberDto.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .build();
    }

    public static MemberDto from(ClassHub_Member classHubMember){
        return MemberDto.builder()
                .memberId(classHubMember.getMemberId())
                .member_name(classHubMember.getMember_name())
                .email(classHubMember.getEmail())
                .uniqueId(classHubMember.getUniqueId())
                .build();
    }
}
