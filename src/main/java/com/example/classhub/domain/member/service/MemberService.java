package com.example.classhub.domain.member.service;

import com.example.classhub.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor //클래스 내부에 final 또는 @NonNull로 표시된 필드만을 매개변수로 하는 생성자를 생성
public class MemberService {
    private final MemberRepository memberRepository;


}
