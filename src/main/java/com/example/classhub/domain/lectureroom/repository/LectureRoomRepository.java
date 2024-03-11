package com.example.classhub.domain.lectureroom.repository;

import com.example.classhub.domain.lectureroom.LectureRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRoomRepository extends JpaRepository<LectureRoom, Long> {
    LectureRoom findByTaInviteCodeAndStInviteCode(String taInviteCode, String stInviteCode);
    List<LectureRoom> findByTaInviteCodeOrStInviteCodeOrName(String taInviteCode, String stInviteCode, String name);
}
