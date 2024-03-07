package com.example.classhub.domain.member.dto;


import com.example.classhub.domain.member.Member;
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

    public static MemberDto from(MemberCreateRequest request){
        return MemberDto.builder()
                .memberId(request.getMemberId())
                .member_name(request.getMember_name())
                .email(request.getEmail())
                .uniqueId(request.getUniqueId())
                .build();
    }

    public static MemberDto from(MemberUpdateRequest request){
        return MemberDto.builder()
                .email(request.getEmail())
                .build();
    }

    public static MemberDto from(Member member){
        return MemberDto.builder()
                .memberId(member.getMemberId())
                .member_name(member.getMember_name())
                .email(member.getEmail())
                .uniqueId(member.getUniqueId())
                .build();
    }
}
