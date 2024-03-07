package com.example.classhub.domain.member.service;

import com.example.classhub.domain.member.Member;
import com.example.classhub.domain.member.controller.response.MemberListResponse;
import com.example.classhub.domain.member.controller.response.MemberResponse;
import com.example.classhub.domain.member.dto.MemberDto;
import com.example.classhub.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //클래스 내부에 final 또는 @NonNull로 표시된 필드만을 매개변수로 하는 생성자를 생성
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public MemberDto createMember(MemberDto memberDto) {
        if (memberRepository.existsByEmail(memberDto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        if (memberRepository.existsByUniqueId(memberDto.getUniqueId())) {
            throw new IllegalArgumentException("이미 사용 중인 유니크 ID입니다.");
        }

        Member member = Member.from(memberDto);
        memberRepository.save(member); // 저장된 Member 객체를 반환받음

        return MemberDto.from(member); // MemberDto로 변환
    }

    public MemberListResponse getMemberList() {
        List<Member> members = memberRepository.findAll();
        List<MemberResponse> memberResponses = members.stream()
                .map(MemberResponse::new)
                .toList();
        return new MemberListResponse(memberResponses);
    }
}
