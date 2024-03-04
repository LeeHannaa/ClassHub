package com.example.classhub.domain.lectureroom;

import com.example.classhub.domain.BaseEntity;
import com.example.classhub.domain.lectureroom.dto.LectureRoomDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LectureRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureRoomId;
    private String name;
    private String taInviteCode;
    private String stInviteCode;
    private boolean onOff;

    public static LectureRoom from(LectureRoomDto lectureRoomDto, String taInviteCode, String stInviteCode) {
        return LectureRoom.builder()
                .name(lectureRoomDto.getName())
                .taInviteCode(taInviteCode)
                .stInviteCode(stInviteCode)
                .onOff(lectureRoomDto.isOnOff())
                .build();
    }
}
