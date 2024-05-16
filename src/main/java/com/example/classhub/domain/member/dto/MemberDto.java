package com.example.classhub.domain.member.dto;


import com.example.classhub.domain.member.ClassHub_Member;
import com.example.classhub.domain.member.controller.request.MemberCreateRequest;
import com.example.classhub.domain.member.controller.request.MemberUpdateRequest;
import com.example.classhub.domain.memberlroom.controller.request.MemberLRoomMemberCreateRequest;
import com.example.classhub.domain.memberlroom.dto.Permission;
import com.example.classhub.domain.memberlroom.dto.Role;
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
    private String department;
    private int login_count;
    private Role role;
    private Permission permission;

    public static MemberDto from(MemberCreateRequest request){
        return MemberDto.builder()
                .memberId(request.getMemberId())
                .member_name(request.getMember_name())
                .email(request.getEmail())
                .uniqueId(request.getUniqueId())
                .department(request.getDepartment())
                .login_count(request.getLogin_count())
                .build();
    }

    public static MemberDto from(MemberUpdateRequest request){
        return MemberDto.builder()
                .email(request.getEmail())
                .department(request.getDepartment())
                .build();
    }

    public static MemberDto from(ClassHub_Member classHubMember){
        return MemberDto.builder()
                .memberId(classHubMember.getMemberId())
                .member_name(classHubMember.getMember_name())
                .email(classHubMember.getEmail())
                .uniqueId(classHubMember.getUniqueId())
                .department(classHubMember.getDepartment())
                .build();
    }

    public static MemberDto from(MemberLRoomMemberCreateRequest memberLRoomMemberCreateRequest){ //강의실에 멤버 추가
        return MemberDto.builder()
                .member_name(memberLRoomMemberCreateRequest.getName())
                .email(memberLRoomMemberCreateRequest.getEmail())
                .uniqueId(memberLRoomMemberCreateRequest.getUniqueId())
                .role(memberLRoomMemberCreateRequest.getRole())
                .permission(memberLRoomMemberCreateRequest.getPermission())
                .build();
    }
}
