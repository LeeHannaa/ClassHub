package com.example.classhub.domain.classhub_lroom.repository;

import com.example.classhub.domain.classhub_lroom.ClassHub_LRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRoomRepository extends JpaRepository<ClassHub_LRoom, Long> {
    ClassHub_LRoom findByTaInviteCodeAndStInviteCode(String taInviteCode, String stInviteCode);
    List<ClassHub_LRoom> findByTaInviteCodeOrStInviteCodeOrRoomName(String taInviteCode, String stInviteCode, String roomName);
}
