package com.example.classhub.domain.memberlroom.service;

import com.example.classhub.domain.classhub_lroom.ClassHub_LRoom;
import com.example.classhub.domain.classhub_lroom.dto.LectureRoomDto;
import com.example.classhub.domain.classhub_lroom.service.LectureRoomService;
import com.example.classhub.domain.member.ClassHub_Member;
import com.example.classhub.domain.member.dto.MemberDto;
import com.example.classhub.domain.member.service.MemberService;
import com.example.classhub.domain.memberlroom.ClassHub_MemberLRoom;
import com.example.classhub.domain.memberlroom.dto.MemberLRoomDto;
import com.example.classhub.domain.memberlroom.dto.Permission;
import com.example.classhub.domain.memberlroom.dto.Role;
import com.example.classhub.domain.memberlroom.repository.MemberLRoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
  public Page<ClassHub_MemberLRoom> findMembersByLRoomId(Long LRoomId, int startAt) {
    Pageable pageable = PageRequest.of(startAt, 7);
    return memberLRoomRepository.findByLectureRoom_lRoomId(LRoomId, pageable);
  }

  public List<ClassHub_MemberLRoom> findMembersByLRoomIdWithoutPaging(Long LRoomId) {
    return memberLRoomRepository.findByLectureRoom_lRoomId(LRoomId, Pageable.unpaged()).getContent();
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
  private Role determineRoleBasedOnCode(String inviteCode, LectureRoomDto lectureRoomDto) {
    if (inviteCode.equals(lectureRoomDto.getStInviteCode())) {
      return Role.STUDENT;
    } else if (inviteCode.equals(lectureRoomDto.getTaInviteCode())) {
      return Role.TA;
    }
    return Role.STUDENT;
  }

  @Transactional
  public void createMemberByOne(LectureRoomDto lectureRoomDto, MemberDto memberDto, String inviteCode) {
    System.out.println("lectureRoomDto.lRoomId : " + lectureRoomDto.getLectureRoomId());
    System.out.println("memberDto.uniqueId: " + memberDto.getUniqueId());
    ClassHub_LRoom lRoom = lectureRoomService.findByRoomIdOne(lectureRoomDto.getLectureRoomId());
    ClassHub_MemberLRoom memberLRoom = memberLRoomRepository.findByLectureRoom_lRoomIdAndClassHubMember_UniqueId(lectureRoomDto.getLectureRoomId(), memberDto.getUniqueId());

    if(memberLRoom == null) {
      Role role = determineRoleBasedOnCode(inviteCode, lectureRoomDto);

      Optional<ClassHub_Member> defaultMember = memberService.findByUniqueId(memberDto.getUniqueId());
      if(defaultMember.isEmpty()){
        ClassHub_Member createMember = memberService.createMember(ClassHub_Member.from(memberDto));
        ClassHub_MemberLRoom memberLRoom1 = ClassHub_MemberLRoom.builder()
                .lectureRoom(lRoom)
                .classHubMember(createMember)
                .permission(Permission.APPROVED)
                .role(role)
                .build();
        memberLRoomRepository.save(memberLRoom1);
      }
      else{
        ClassHub_MemberLRoom memberLRoom1 = ClassHub_MemberLRoom.builder()
                .lectureRoom(lRoom)
                .classHubMember(defaultMember.get())
                .permission(Permission.APPROVED)
                .role(role)
                .build();
        memberLRoomRepository.save(memberLRoom1);
      }
    }
  }
  @Transactional
  public Boolean createMemberByOne(LectureRoomDto lectureRoomDto, MemberDto memberDto) {
    System.out.println("lectureRoomDto.lRoomId : " + lectureRoomDto.getLectureRoomId());
    System.out.println("memberDto.uniqueId: " + memberDto.getUniqueId());
    ClassHub_LRoom lRoom = lectureRoomService.findByRoomIdOne(lectureRoomDto.getLectureRoomId());
    ClassHub_MemberLRoom memberLRoom = memberLRoomRepository.findByLectureRoom_lRoomIdAndClassHubMember_UniqueId(lectureRoomDto.getLectureRoomId(), memberDto.getUniqueId());

    if(memberLRoom == null) {
      // member에서 해당 member 찾기
      Optional<ClassHub_Member> defaultMember = memberService.findByUniqueId(memberDto.getUniqueId());
      if(defaultMember.isEmpty()){
        ClassHub_Member createMember = memberService.createMember(ClassHub_Member.from(memberDto));
        ClassHub_MemberLRoom memberLRoom1 = ClassHub_MemberLRoom.builder()
                .lectureRoom(lRoom)
                .classHubMember(createMember)
                .permission(memberDto.getPermission())
                .role(memberDto.getRole())
                .build();
        memberLRoomRepository.save(memberLRoom1);
      }
      else{
        ClassHub_MemberLRoom memberLRoom1 = ClassHub_MemberLRoom.builder()
                .lectureRoom(lRoom)
                .classHubMember(defaultMember.get())
                .permission(memberDto.getPermission())
                .role(memberDto.getRole())
                .build();
        memberLRoomRepository.save(memberLRoom1);
      }
      return true;
    }
    else{
      return false;
    }
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

  public MemberLRoomDto findByMemberIdAndLroomId(Long memberId, Long lRoomId) {
    Optional<ClassHub_MemberLRoom> memberLRoomOpt = memberLRoomRepository.findByClassHubMember_MemberIdAndLectureRoom_lRoomId(memberId, lRoomId);
    if (memberLRoomOpt.isPresent()) {
      ClassHub_MemberLRoom memberLRoom = memberLRoomOpt.get();
      // 예시에서는 MemberLRoomDto 생성자와 필요한 데이터를 가정합니다.
      // 실제로는 MemberLRoomDto 클래스의 정의와 생성자에 따라 다를 수 있습니다.
      MemberLRoomDto memberLRoomDto = new MemberLRoomDto(
        memberLRoom.getId(),
        memberLRoom.getRole(),
        memberLRoom.getPermission()
      );
      return memberLRoomDto;
    } else {
      // 데이터가 없는 경우 적절한 처리를 합니다. 여기서는 null을 반환합니다.
      // 실제로는 예외를 발생시키거나, Optional.empty() 등을 활용할 수 있습니다.
      return null;
    }
  }
}
