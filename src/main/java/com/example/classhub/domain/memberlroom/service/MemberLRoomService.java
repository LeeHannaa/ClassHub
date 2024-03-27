package com.example.classhub.domain.memberlroom.service;

import com.example.classhub.domain.memberlroom.MemberLRoom;
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
  public MemberLRoom createMemberLRoom(MemberLRoom memberLRoom) {
    return memberLRoomRepository.save(memberLRoom);
  }

  // Read All
  public List<MemberLRoom> findAllMemberLRooms() {
    return memberLRoomRepository.findAll();
  }

  // Read One
  public Optional<MemberLRoom> findMemberLRoomById(Long id) {
    return memberLRoomRepository.findById(id);
  }

  // Update
  public MemberLRoom updateMemberLRoom(Long id, MemberLRoom memberLRoomDetails) {
    MemberLRoom memberLRoom = memberLRoomRepository.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("MemberLRoom with id " + id + " not found"));
    memberLRoom.setClassHubMember(memberLRoomDetails.getClassHubMember());
    memberLRoom.setLectureRoom(memberLRoomDetails.getLectureRoom());
    memberLRoom.setRole(memberLRoomDetails.getRole());
    memberLRoom.setPermission(memberLRoomDetails.getPermission());
    return memberLRoomRepository.save(memberLRoom);
  }

  // Delete
  public void deleteMemberLRoom(Long id) {
    MemberLRoom memberLRoom = memberLRoomRepository.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("MemberLRoom with id " + id + " not found"));
    memberLRoomRepository.delete(memberLRoom);
  }
}
