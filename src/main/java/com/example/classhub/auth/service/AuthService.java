package com.example.classhub.auth.service;

import com.example.classhub.domain.member.ClassHub_Member;
import com.example.classhub.domain.member.dto.MemberDto;
import com.example.classhub.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    public MemberDto login(MemberDto memberDto) {
        ClassHub_Member member = memberRepository.findByUniqueId(memberDto.getUniqueId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        if(member == null){
            ClassHub_Member newMember = ClassHub_Member.from(memberDto);
            memberRepository.save(newMember);
            return MemberDto.builder()
                    .uniqueId(newMember.getUniqueId())
                    .member_name(newMember.getMember_name())
                    .email(newMember.getEmail())
                    .department(newMember.getDepartment())
                    .build();
        }
//        if (user.isEmpty()) {
//            User newUser=User.from(dto);
//            userRepository.save(User.from(dto));
//            return AuthDto.builder()
//                    .token(JwtUtil.createToken(newUser.getUniqueId(), newUser.getStatus().name(), newUser.getName() , SECRET_KEY))
//                    .build();
        else {
            member.update(memberDto);
            return MemberDto.builder()
//                    .token(
//                            JwtUtil.createToken(
//                                    user.get().getUniqueId(), user.get().getStatus().name(), user.get().getName() , SECRET_KEY))
                    .build();
        }
    }
}
