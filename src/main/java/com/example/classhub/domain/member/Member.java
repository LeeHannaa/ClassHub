package com.example.classhub.domain.member;

import com.example.classhub.domain.BaseEntity;
import com.example.classhub.domain.member.dto.MemberDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String member_name;
    private String email;
    private String uniqueId;
    private String nickname;
    private int login_count;

    public static Member from(MemberDto memberDto) {
        return Member.builder()
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
