package com.example.classhub.domain.datadetail.dto;

import com.example.classhub.domain.datadetail.ClassHub_DataDetail;
import com.example.classhub.domain.datadetail.controller.request.DataDetailCreateRequest;
import com.example.classhub.domain.datadetail.controller.request.DataDetailUpdateRequest;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DataDetailDto {
    private Long id;
    private String studentNum;
    private Double score;
    private String comment;
    private Long tagId;

    public static DataDetailDto from(DataDetailCreateRequest request) {
        return DataDetailDto.builder()
                .studentNum(request.getStudentNum())
                .score(request.getScore())
                .comment(request.getComment())
                .tagId(request.getTagId())
                .build();
    }

    public static DataDetailDto from(DataDetailUpdateRequest request) {
        return DataDetailDto.builder()
                .studentNum(request.getStudentNum())
                .score(request.getScore())
                .comment(request.getComment())
                .build();
    }

    public static DataDetailDto from(ClassHub_DataDetail detail) {
        return DataDetailDto.builder()
                .id(detail.getId())
                .studentNum(detail.getStudentNum())
                .score(detail.getScore())
                .comment(detail.getComment())
                .tagId(detail.getTag().getTagId())
                .build();
    }
}
