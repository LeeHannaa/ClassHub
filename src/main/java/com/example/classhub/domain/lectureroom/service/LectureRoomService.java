package com.example.classhub.domain.lectureroom.service;

import com.example.classhub.domain.lectureroom.LectureRoom;
import com.example.classhub.domain.lectureroom.controller.response.LectureRoomListResponse;
import com.example.classhub.domain.lectureroom.controller.response.LectureRoomResponse;
import com.example.classhub.domain.lectureroom.dto.LectureRoomDto;
import com.example.classhub.domain.lectureroom.repository.LectureRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureRoomService {
    private final LectureRoomRepository lectureRoomRepository;

    @Transactional
    public LectureRoomDto createLectureRoom(LectureRoomDto lectureRoomDto) {
        String taInviteCode;
        String stInviteCode;
        do {
            taInviteCode = generateInviteCode();
            stInviteCode = generateInviteCode();
        } while (taInviteCode.equals(stInviteCode) || lectureRoomRepository.findByTaInviteCodeAndStInviteCode(taInviteCode, stInviteCode) != null);

        LectureRoom lectureRoom = LectureRoom.from(lectureRoomDto, taInviteCode, stInviteCode);
        lectureRoomRepository.save(lectureRoom);

        return LectureRoomDto.from(lectureRoom);
    }

//    @Transactional
    public LectureRoomListResponse getLectureRoomList() {
        List<LectureRoom> lectureRooms = lectureRoomRepository.findAll();
        List<LectureRoomResponse> lectureRoomResponses = lectureRooms.stream()
                .map(LectureRoomResponse::new)
                .collect(Collectors.toList());
        return new LectureRoomListResponse(lectureRoomResponses);
    }
    @Transactional
    public LectureRoomDto findByRoomId(Long lectureRoomId) {
        LectureRoom lectureRoom = lectureRoomRepository.findById(lectureRoomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의실이 존재하지 않습니다."));
        return LectureRoomDto.from(lectureRoom);
    }
    @Transactional
    public LectureRoomDto update(Long lectureRoomId, LectureRoomDto lectureRoomDto) {
        LectureRoom lectureRoom = lectureRoomRepository.findById(lectureRoomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의실이 존재하지 않습니다."));

        lectureRoom.update(lectureRoomDto);
        lectureRoomRepository.save(lectureRoom);
        return LectureRoomDto.from(lectureRoom);
    }
    @Transactional
    public void delete(Long lectureRoomId) {
        lectureRoomRepository.deleteById(lectureRoomId);
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
