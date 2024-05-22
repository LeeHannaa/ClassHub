package com.example.classhub.domain.member.service;

import com.example.classhub.domain.member.ClassHub_Member;
import com.example.classhub.domain.member.controller.response.MemberListResponse;
import com.example.classhub.domain.member.controller.response.MemberResponse;
import com.example.classhub.domain.member.dto.MemberDto;
import com.example.classhub.domain.member.repository.MemberRepository;
import com.example.classhub.domain.memberlroom.controller.request.MemberLRoomMemberCreateRequest;
//import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //클래스 내부에 final 또는 @NonNull로 표시된 필드만을 매개변수로 하는 생성자를 생성
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public MemberDto createMember(MemberDto memberDto) {
        ClassHub_Member existingMember = memberRepository.findByUniqueId(memberDto.getUniqueId()).orElse(null);
        if (existingMember != null) {
            return MemberDto.from(existingMember);
        }
        ClassHub_Member classHubMember = ClassHub_Member.from(memberDto);
        memberRepository.save(classHubMember);
        return MemberDto.from(classHubMember);
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
                .collect(Collectors.toList());
        return new MemberListResponse(memberResponses);
    }

    @Transactional
    public MemberDto findMemberDtoByMemberId(Long memberId) {
        ClassHub_Member classHubMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        return MemberDto.from(classHubMember);
    }

    @Transactional
    public ClassHub_Member findByMemberId(Long memberId) {
        ClassHub_Member classHubMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        return classHubMember;
    }

    @Transactional
    public Optional<ClassHub_Member> findByUniqueId(String uniqueId) {
        return memberRepository.findByUniqueId(uniqueId);
    }

    @Transactional
    public MemberDto findByUniqueIdDto(String uniqueId) {
        ClassHub_Member classHubMember = memberRepository.findByUniqueId(uniqueId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        return MemberDto.from(classHubMember);
    }

    @Transactional
    public String findNameByUniqueId(String uniqueId) {
      return memberRepository.findByUniqueId(uniqueId)
        .map(ClassHub_Member::getMember_name)
        .orElse("Unknown"); // 해당 uniqueId를 가진 학생이 없을 경우 "Unknown" 반환
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
