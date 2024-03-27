package com.example.classhub.domain.datadetail.controller.response;

import com.example.classhub.domain.datadetail.ClassHub_DataDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class DataDetailResponse {
    private Long id;
    private String studentNum;
    private Double score;
    private String comment;

    public DataDetailResponse(ClassHub_DataDetail detail){
        this.id = detail.getId();
        this.studentNum = detail.getStudentNum();
        this.score = detail.getScore();
        this.comment = detail.getComment();
    }
}