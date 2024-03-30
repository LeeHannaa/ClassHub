package com.example.classhub.domain.classhub_lroom;

import com.example.classhub.domain.BaseEntity;
import com.example.classhub.domain.classhub_lroom.dto.LectureRoomDto;
import com.example.classhub.domain.memberlroom.ClassHub_MemberLRoom;
import com.example.classhub.domain.tag.ClassHub_Tag;
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
public class ClassHub_LRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lRoomId;
    private String roomName;
    private String taInviteCode;
    private String stInviteCode;
    private String creator;
    private String description;
    private boolean onOff;

    @OneToMany(mappedBy = "lectureRoom")
    private List<ClassHub_Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "lectureRoom")
    private List<ClassHub_MemberLRoom> classHubMemberLRooms = new ArrayList<>();

    public static ClassHub_LRoom from(LectureRoomDto lectureRoomDto, String taInviteCode, String stInviteCode) {
        return ClassHub_LRoom.builder()
                .roomName(lectureRoomDto.getRoomName())
                .taInviteCode(taInviteCode)
                .stInviteCode(stInviteCode)
                .description(lectureRoomDto.getDescription())
                .onOff(lectureRoomDto.isOnOff())
                .build();
    }

    public static ClassHub_LRoom from(LectureRoomDto lectureRoomDto) {
        return ClassHub_LRoom.builder()
                .roomName(lectureRoomDto.getRoomName())
                .taInviteCode(lectureRoomDto.getTaInviteCode())
                .stInviteCode(lectureRoomDto.getStInviteCode())
                .description(lectureRoomDto.getDescription())
                .onOff(lectureRoomDto.isOnOff())
                .build();
    }

    public void update(LectureRoomDto lectureRoomDto) {
        this.description = lectureRoomDto.getDescription();
        this.roomName = lectureRoomDto.getRoomName();
        this.onOff = lectureRoomDto.isOnOff();
    }
}
