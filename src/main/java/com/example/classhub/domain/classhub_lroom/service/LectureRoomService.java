package com.example.classhub.domain.classhub_lroom.service;

import com.example.classhub.domain.classhub_lroom.ClassHub_LRoom;
import com.example.classhub.domain.classhub_lroom.controller.response.LectureRoomListResponse;
import com.example.classhub.domain.classhub_lroom.controller.response.LectureRoomResponse;
import com.example.classhub.domain.classhub_lroom.dto.LectureRoomDto;
import com.example.classhub.domain.classhub_lroom.repository.LectureRoomRepository;
import com.example.classhub.domain.tag.ClassHub_Tag;
import com.example.classhub.domain.tag.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureRoomService {
    private final LectureRoomRepository lectureRoomRepository;
    private final TagRepository tagRepository;

    @Transactional
    public LectureRoomDto createLectureRoom(LectureRoomDto lectureRoomDto) {
        String taInviteCode;
        String stInviteCode;
        do {
            taInviteCode = generateInviteCode();
            stInviteCode = generateInviteCode();
        } while (taInviteCode.equals(stInviteCode) || lectureRoomRepository.findByTaInviteCodeAndStInviteCode(taInviteCode, stInviteCode) != null);

        ClassHub_LRoom lectureRoom = ClassHub_LRoom.from(lectureRoomDto, taInviteCode, stInviteCode);
        lectureRoomRepository.save(lectureRoom);

        return LectureRoomDto.from(lectureRoom);
    }

    @Transactional
    public LectureRoomListResponse getLectureRoomList() {
        List<ClassHub_LRoom> lectureRooms = lectureRoomRepository.findAll();
        List<LectureRoomResponse> lectureRoomResponses = lectureRooms.stream()
                .map(LectureRoomResponse::new)
                .collect(Collectors.toList());
        return new LectureRoomListResponse(lectureRoomResponses);
    }
    @Transactional
    public LectureRoomListResponse findByKeyword(String keyword) {
        // 이름, TA 초대 코드 또는 학생 초대 코드 중 하나라도 입력한 키워드와 일치하는 강의실 정보를 조회
        List<ClassHub_LRoom> lectureRooms = lectureRoomRepository.findByTaInviteCodeOrStInviteCodeOrRoomName(keyword, keyword, keyword);
        List<LectureRoomResponse> lectureRoomResponses = lectureRooms.stream()
                .map(LectureRoomResponse::new)
                .collect(Collectors.toList());
        return new LectureRoomListResponse(lectureRoomResponses);
    }
    @Transactional
    public LectureRoomDto findByRoomId(Long lectureRoomId) {
        ClassHub_LRoom lectureRoom = lectureRoomRepository.findById(lectureRoomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의실이 존재하지 않습니다."));
        return LectureRoomDto.from(lectureRoom);
    }
    @Transactional
    public ClassHub_LRoom findExistingLectureRoom(Long lectureRoomId) {
        return lectureRoomRepository.findById(lectureRoomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의실이 존재하지 않습니다."));
    }
    @Transactional
    public LectureRoomDto update(Long lectureRoomId, LectureRoomDto lectureRoomDto) {
        ClassHub_LRoom lectureRoom = lectureRoomRepository.findById(lectureRoomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의실이 존재하지 않습니다."));

        lectureRoom.update(lectureRoomDto);
        lectureRoomRepository.save(lectureRoom);
        return LectureRoomDto.from(lectureRoom);
    }
    @Transactional
    public void delete(Long lectureRoomId) {
        Optional<ClassHub_LRoom> optionalLectureRoom = lectureRoomRepository.findById(lectureRoomId);
        optionalLectureRoom.ifPresent(lectureRoom -> {
            List<ClassHub_Tag> tags = tagRepository.findByLectureRoom(lectureRoom);
            tagRepository.deleteAll(tags);

            lectureRoomRepository.deleteById(lectureRoomId);
        });
    }

    private String generateInviteCode() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 8;

        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
