package com.example.classhub.domain.memberlroom.service;

import com.example.classhub.domain.classhub_lroom.ClassHub_LRoom;
import com.example.classhub.domain.classhub_lroom.service.LectureRoomService;
import com.example.classhub.domain.member.ClassHub_Member;
import com.example.classhub.domain.member.dto.MemberDto;
import com.example.classhub.domain.member.service.MemberService;
import com.example.classhub.domain.memberlroom.ClassHub_MemberLRoom;
import com.example.classhub.domain.memberlroom.dto.Permission;
import com.example.classhub.domain.memberlroom.dto.Role;
import com.example.classhub.domain.memberlroom.repository.MemberLRoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MemberLRoomService {

  private final MemberLRoomRepository memberLRoomRepository;
  private final MemberService memberService;
  private final LectureRoomService lectureRoomService;

  @Autowired
  public MemberLRoomService(MemberLRoomRepository memberLRoomRepository, MemberService memberService, LectureRoomService lectureRoomService) {
    this.memberLRoomRepository = memberLRoomRepository;
      this.memberService = memberService;
      this.lectureRoomService = lectureRoomService;
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
  public ClassHub_MemberLRoom updateMemberLRoomRole(Long id, ClassHub_MemberLRoom classHubMemberLRoomDetails) {
    ClassHub_MemberLRoom classHubMemberLRoom = memberLRoomRepository.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("MemberLRoom with id " + id + " not found"));
    classHubMemberLRoom.setRole(classHubMemberLRoomDetails.getRole());
    return memberLRoomRepository.save(classHubMemberLRoom);
  }
  public ClassHub_MemberLRoom updateMemberLRoomPermission(Long id, ClassHub_MemberLRoom classHubMemberLRoomDetails) {
    ClassHub_MemberLRoom classHubMemberLRoom = memberLRoomRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("MemberLRoom with id " + id + " not found"));
    classHubMemberLRoom.setPermission(classHubMemberLRoomDetails.getPermission());
    return memberLRoomRepository.save(classHubMemberLRoom);
  }

  // Delete
  public void deleteMemberLRoom(Long id) {
    ClassHub_MemberLRoom classHubMemberLRoom = memberLRoomRepository.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("MemberLRoom with id " + id + " not found"));
    memberLRoomRepository.delete(classHubMemberLRoom);
  }

  private String removeUtf8Bom(String s) {
    if (s.startsWith("\uFEFF")) {
      return s.substring(1);
    }
    return s;
  }

  public void createMemberByOne(Long lRoomId, ClassHub_MemberLRoom classHubMemberLRoom, ClassHub_Member classHubMember) {
    //ToDo:
    // 1. 해당 강의실에 classHubMember와 같은 학번 가진 학생 있는지 파악
      // 1-1. 있다면 추가 불가능 사인 보내주기
      // 1-2. 없다면 ClassHub_Member에서 해당 학생 id 찾아오기
    // 2. ClassHub_Member에서 해당 학생 id가 없다면 classHubMember와 관련해서 추가해주기
    // 3. ClassHub_Member에서 해당 학생 id가 있다면 그 id만 가져오기
    // 4. 가져온 id를 바탕으로 ClassHub_MemberLRoom추가하기
    
  }

  @Transactional
  public void createMemberLRoom(Long lRoomId, MultipartFile studentFile) {
    ClassHub_LRoom lRoom = lectureRoomService.findExistingLectureRoom(lRoomId);

    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(studentFile.getInputStream(), StandardCharsets.UTF_8));
      String line = reader.readLine();  // 첫 번째 줄 읽기 (헤더)
      if (line != null && !line.isEmpty()) {
        line = removeUtf8Bom(line);
      }
      String[] headers = line.split(",");  // 헤더 파싱

      Map<String, Integer> headerMap = new HashMap<>();
      for (int i = 0; i < headers.length; i++) {
        headerMap.put(headers[i].trim(), i);  // 공백 제거 및 맵에 추가
      }

      while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");

        String uniqueId = headerMap.containsKey("학번") ? data[headerMap.get("학번")] : null;
        Optional<ClassHub_Member> existingMember = memberService.findByUniqueId(uniqueId);

        ClassHub_Member member = existingMember.orElseGet(() -> ClassHub_Member.builder()
                .member_name(headerMap.containsKey("이름") ? data[headerMap.get("이름")] : null)
                .department(headerMap.containsKey("학부") ? data[headerMap.get("학부")] : null)
                .uniqueId(uniqueId)
                .build());

        if (existingMember.isEmpty()) {
          memberService.createMember(member);
        }

        ClassHub_MemberLRoom classHubMemberLRoom = ClassHub_MemberLRoom.builder()
                .lectureRoom(lRoom)
                .classHubMember(member)
                .role(Role.STUDENT)
                .permission(Permission.UNAPPROVED)
                .build();

        memberLRoomRepository.save(classHubMemberLRoom);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Failed to process student file", e);
    }
  }
}
