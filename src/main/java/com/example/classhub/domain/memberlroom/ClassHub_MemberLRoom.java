package com.example.classhub.domain.memberlroom;

import com.example.classhub.domain.BaseEntity;
import com.example.classhub.domain.classhub_lroom.ClassHub_LRoom;
import com.example.classhub.domain.member.ClassHub_Member;
import com.example.classhub.domain.memberlroom.dto.Permission;
import com.example.classhub.domain.memberlroom.dto.Role;
//import jakarta.persistence.*;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ClassHub_MemberLRoom extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "classHubMemberId")
  private ClassHub_Member classHubMember;

  @ManyToOne
  @JoinColumn(name = "lRoomId")
  private ClassHub_LRoom lectureRoom;

  @Enumerated(EnumType.STRING)
  private Role role = Role.STUDENT;
  @Enumerated(EnumType.STRING)
  private Permission permission = Permission.UNAPPROVED;

  public void setClassHubMember(ClassHub_Member classHubMember) {
    this.classHubMember = classHubMember;
    classHubMember.getClassHubMemberLRooms().add(this);
  }

  public void setLectureRoom(ClassHub_LRoom lectureRoom) {
    this.lectureRoom = lectureRoom;
    lectureRoom.getClassHubMemberLRooms().add(this);
  }


}
