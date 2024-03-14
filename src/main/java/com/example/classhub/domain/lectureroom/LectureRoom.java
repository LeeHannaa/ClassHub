package com.example.classhub.domain.lectureroom;

import com.example.classhub.domain.BaseEntity;
import com.example.classhub.domain.lectureroom.dto.LectureRoomDto;
import com.example.classhub.domain.tag.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private String creator;
    private boolean onOff;

    @OneToMany(mappedBy = "lectureRoom")
    private List<Tag> tags = new ArrayList<>();


    public static LectureRoom from(LectureRoomDto lectureRoomDto, String taInviteCode, String stInviteCode) {
        return LectureRoom.builder()
                .name(lectureRoomDto.getName())
                .taInviteCode(taInviteCode)
                .stInviteCode(stInviteCode)
                .onOff(lectureRoomDto.isOnOff())
                .build();
    }

    public void update(LectureRoomDto lectureRoomDto) {
        this.name = lectureRoomDto.getName();
        this.onOff = lectureRoomDto.isOnOff();
    }
}
