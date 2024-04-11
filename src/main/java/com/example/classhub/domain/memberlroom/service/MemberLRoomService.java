package com.example.classhub.domain.memberlroom.service;

import com.example.classhub.domain.member.ClassHub_Member;
import com.example.classhub.domain.memberlroom.ClassHub_MemberLRoom;
import com.example.classhub.domain.memberlroom.repository.MemberLRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberLRoomService {

  private final MemberLRoomRepository memberLRoomRepository;

  @Autowired
  public MemberLRoomService(MemberLRoomRepository memberLRoomRepository) {
    this.memberLRoomRepository = memberLRoomRepository;
  }

  // Create
  public ClassHub_MemberLRoom createMemberLRoom(ClassHub_MemberLRoom classHubMemberLRoom) {
    return memberLRoomRepository.save(classHubMemberLRoom);
  }

  // Read All
  public List<ClassHub_MemberLRoom> findAllMemberLRooms() {
    return memberLRoomRepository.findAll();
  }


  // Read One
  public Optional<ClassHub_MemberLRoom> findMemberLRoomById(Long id) {
    return memberLRoomRepository.findById(id);
  }

  // 강의실 아이디에 따라 해당 멤버 불러오기
  public List<ClassHub_MemberLRoom> findMembersByLRoomId(Long LRoomId) {
    return memberLRoomRepository.findByLectureRoom_lRoomId(LRoomId);
  }
  // 강의실 아이디에 따라 강의실 정보 불러오기
  public ClassHub_MemberLRoom findMemberByLRoomId(Long LRoomId) {
    List<ClassHub_MemberLRoom> classHub_memberLRoom = memberLRoomRepository.findByLectureRoom_lRoomId(LRoomId);
    ClassHub_MemberLRoom result = classHub_memberLRoom.get(0);
    return result;
  }


  // Update
  public ClassHub_MemberLRoom updateMemberLRoom(Long id, ClassHub_MemberLRoom classHubMemberLRoomDetails) {
    ClassHub_MemberLRoom classHubMemberLRoom = memberLRoomRepository.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("MemberLRoom with id " + id + " not found"));
    classHubMemberLRoom.setClassHubMember(classHubMemberLRoomDetails.getClassHubMember());
    classHubMemberLRoom.setLectureRoom(classHubMemberLRoomDetails.getLectureRoom());
    classHubMemberLRoom.setRole(classHubMemberLRoomDetails.getRole());
    classHubMemberLRoom.setPermission(classHubMemberLRoomDetails.getPermission());
    return memberLRoomRepository.save(classHubMemberLRoom);
  }

  // Delete
  public void deleteMemberLRoom(Long id) {
    ClassHub_MemberLRoom classHubMemberLRoom = memberLRoomRepository.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("MemberLRoom with id " + id + " not found"));
    memberLRoomRepository.delete(classHubMemberLRoom);
  }
}
