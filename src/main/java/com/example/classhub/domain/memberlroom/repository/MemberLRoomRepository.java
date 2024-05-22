package com.example.classhub.domain.memberlroom.repository;

import com.example.classhub.domain.memberlroom.ClassHub_MemberLRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberLRoomRepository extends JpaRepository<ClassHub_MemberLRoom, Long> {
    Optional<ClassHub_MemberLRoom> findByClassHubMember_MemberIdAndLectureRoom_lRoomId(Long classHubMemberId, Long lRoomId); // 반환 유형 수정
    List<ClassHub_MemberLRoom> findByLectureRoom_lRoomId(Long lRoomId);
    ClassHub_MemberLRoom findByLectureRoom_lRoomIdAndClassHubMember_UniqueId(Long lRoomId, String uniqueId);
    List<ClassHub_MemberLRoom> findByClassHubMemberMemberId(Long memberId);
}
