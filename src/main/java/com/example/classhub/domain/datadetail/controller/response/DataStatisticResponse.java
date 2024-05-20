package com.example.classhub.domain.datadetail.controller.response;

import com.example.classhub.domain.classhub_lroom.ClassHub_LRoom;
import com.example.classhub.domain.datadetail.ClassHub_DataDetail;
import com.example.classhub.domain.member.ClassHub_Member;
import com.example.classhub.domain.memberlroom.ClassHub_MemberLRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class DataStatisticResponse {
    private Long id;
    private String studentNum;
    private Double score;
    private String comment;
    private Long tagId;
    private String studentName;
    private String tagName;
    private Long lRoomId;
    private String name;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public DataStatisticResponse(ClassHub_DataDetail detail){
        this.id = detail.getId();
        this.studentNum = detail.getStudentNum();
        this.score = detail.getScore();
        this.comment = detail.getComment();
        this.tagId = detail.getTag().getTagId();
        this.studentName = detail.getTag().getLectureRoom().getClassHubMemberLRooms()
                .stream()
                .filter(memberLRoom -> memberLRoom.getClassHubMember().getUniqueId().equals(detail.getStudentNum()))
                .map(memberLRoom -> memberLRoom.getClassHubMember().getMember_name())
                .findFirst()
                .orElse(null);
        this.tagName = detail.getTag().getName();
        this.lRoomId = detail.getTag().getLectureRoom().getLRoomId();
        this.regDate = detail.getRegDate();
        this.modDate = detail.getModDate();

        System.out.println("getLectureRoom" + detail.getTag().getLectureRoom().getRoomName());
    }
}
