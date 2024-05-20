package com.example.classhub.domain.memberlroom.repository;

import com.example.classhub.domain.member.ClassHub_Member;
import com.example.classhub.domain.memberlroom.ClassHub_MemberLRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberLRoomRepository extends JpaRepository<ClassHub_MemberLRoom, Long> {
    List<ClassHub_MemberLRoom> findByLectureRoom_lRoomId(Long lRoomId);
    ClassHub_MemberLRoom findByLectureRoom_lRoomIdAndClassHubMember_UniqueId(Long lRoomId, String uniqueId);
    List<ClassHub_MemberLRoom> findByClassHubMemberMemberId(Long memberId);
}
