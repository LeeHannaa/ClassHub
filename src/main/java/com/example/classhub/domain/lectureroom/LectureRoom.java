package com.example.classhub.domain.lectureroom;

import com.example.classhub.domain.BaseEntity;
import com.example.classhub.domain.lectureroom.dto.LectureRoomDto;
import com.example.classhub.domain.memberlroom.MemberLRoom;
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
    private String l_room_name;
    private String taInviteCode;
    private String stInviteCode;
    private boolean onOff;
    private String description;

  @OneToMany(mappedBy = "lectureRoom")
  private List<Tag> tags = new ArrayList<>();

  @OneToMany(mappedBy = "lectureRoom")
  private List<MemberLRoom> memberLRooms = new ArrayList<>();
    public static LectureRoom from(LectureRoomDto lectureRoomDto, String taInviteCode, String stInviteCode) {
        return LectureRoom.builder()
                .l_room_name(lectureRoomDto.getL_room_name())
                .taInviteCode(taInviteCode)
                .stInviteCode(stInviteCode)
                .onOff(lectureRoomDto.isOnOff())
                .build();
    }

    public void update(LectureRoomDto lectureRoomDto) {
        this.l_room_name = lectureRoomDto.getL_room_name();
        this.onOff = lectureRoomDto.isOnOff();
    }
}
