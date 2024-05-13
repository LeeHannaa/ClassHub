package com.example.classhub.domain.member.service;

import com.example.classhub.domain.member.ClassHub_Member;
import com.example.classhub.domain.member.controller.response.MemberListResponse;
import com.example.classhub.domain.member.controller.response.MemberResponse;
import com.example.classhub.domain.member.dto.MemberDto;
import com.example.classhub.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor //클래스 내부에 final 또는 @NonNull로 표시된 필드만을 매개변수로 하는 생성자를 생성
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public MemberDto createMember(MemberDto memberDto) {
//        if (memberRepository.existsByEmail(memberDto.getEmail())) {
//            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
//        }

        if (memberRepository.existsByUniqueId(memberDto.getUniqueId())) {
            throw new IllegalArgumentException("이미 사용 중인 유니크 ID입니다.");
        }

        ClassHub_Member classHubMember = ClassHub_Member.from(memberDto);
        memberRepository.save(classHubMember); // 저장된 Member 객체를 반환받음

        return MemberDto.from(classHubMember); // MemberDto로 변환
    }
    @Transactional
    public ClassHub_Member createMember(ClassHub_Member classHubMember) {
        if (memberRepository.existsByUniqueId(classHubMember.getUniqueId())) {
            throw new IllegalArgumentException("이미 사용 중인 유니크 ID입니다.");
        }
        return memberRepository.save(classHubMember);
    }

    public MemberListResponse getMemberList() {
        List<ClassHub_Member> classHubMembers = memberRepository.findAll();
        List<MemberResponse> memberResponses = classHubMembers.stream()
                .map(MemberResponse::new)
                .toList();
        return new MemberListResponse(memberResponses);
    }

    @Transactional
    public MemberDto findByMemberId(Long memberId) {
        ClassHub_Member classHubMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        return MemberDto.from(classHubMember);
    }

    @Transactional
    public Optional<ClassHub_Member> findByUniqueId(String uniqueId) {
        return memberRepository.findByUniqueId(uniqueId);
    }
    
    @Transactional
    public void delete(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    @Transactional
    public MemberDto update(Long memberId, MemberDto memberDto) {
      ClassHub_Member classHubMember = memberRepository.findById(memberId)
              .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

      classHubMember.update(memberDto);
      memberRepository.save(classHubMember);
      return MemberDto.from(classHubMember);
    }


}
