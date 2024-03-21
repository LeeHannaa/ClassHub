package com.example.classhub.domain.memberlroom;

import com.example.classhub.domain.lectureroom.LectureRoom;
import com.example.classhub.domain.member.Member;
import com.example.classhub.domain.memberlroom.dto.Permission;
import com.example.classhub.domain.memberlroom.dto.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
public class MemberLRoom {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long memberLRoomId;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne
  @JoinColumn(name = "l_room_id")
  private LectureRoom lectureRoom;

  @Enumerated(EnumType.STRING)
  private Role role = Role.STUDENT;
  @Enumerated(EnumType.STRING)
  private Permission permission = Permission.UNAPPROVED;

  public void setMember(Member member) {
    this.member = member;
    member.getMemberLRooms().add(this);
  }

  public void setLectureRoom(LectureRoom lectureRoom) {
    this.lectureRoom = lectureRoom;
    lectureRoom.getMemberLRooms().add(this);
  }


}
