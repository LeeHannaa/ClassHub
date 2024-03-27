package com.example.classhub.domain.member;

import com.example.classhub.domain.BaseEntity;
import com.example.classhub.domain.member.dto.MemberDto;
import com.example.classhub.domain.memberlroom.ClassHub_MemberLRoom;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassHub_Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String member_name;
    private String email;
    private String uniqueId;
    private String nickname;
    private int login_count;

    @OneToMany(mappedBy = "classHubMember")
    private List<ClassHub_MemberLRoom> classHubMemberLRooms = new ArrayList<>();
    public static ClassHub_Member from(MemberDto memberDto) {
        return ClassHub_Member.builder()
                .member_name(memberDto.getMember_name())
                .email(memberDto.getEmail())
                .uniqueId(memberDto.getUniqueId())
                .nickname(memberDto.getNickname())
                .login_count(memberDto.getLogin_count())
                .build();
    }

    public void update(MemberDto memberDto) {
        this.member_name = memberDto.getMember_name();
        this.email = memberDto.getEmail();
        this.uniqueId = memberDto.getUniqueId();
    }

}
