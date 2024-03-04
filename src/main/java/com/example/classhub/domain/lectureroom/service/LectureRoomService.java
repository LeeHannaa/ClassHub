package com.example.classhub.domain.lectureroom.service;

import com.example.classhub.domain.lectureroom.LectureRoom;
import com.example.classhub.domain.lectureroom.dto.LectureRoomDto;
import com.example.classhub.domain.lectureroom.repository.LectureRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

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
