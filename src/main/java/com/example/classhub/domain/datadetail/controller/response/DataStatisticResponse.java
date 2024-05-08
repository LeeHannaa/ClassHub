package com.example.classhub.domain.datadetail.controller.response;

import com.example.classhub.domain.classhub_lroom.ClassHub_LRoom;
import com.example.classhub.domain.datadetail.ClassHub_DataDetail;
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
        this.tagName = detail.getTag().getName();
        this.lRoomId = detail.getTag().getLectureRoom().getLRoomId();
        this.regDate = detail.getRegDate();
        this.modDate = detail.getModDate();

        System.out.println("getLectureRoom" + detail.getTag().getLectureRoom().getRoomName());
    }
}
