package com.example.classhub.domain.datadetail;

import com.example.classhub.domain.BaseEntity;
import com.example.classhub.domain.datadetail.dto.DataDetailDto;
import com.example.classhub.domain.tag.ClassHub_Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassHub_DataDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentNum;
    private Double score;
    private String comment;

    @ManyToOne
    private ClassHub_Tag tag;

    public static ClassHub_DataDetail from(DataDetailDto dataDetailDto, ClassHub_Tag tag) {
        return ClassHub_DataDetail.builder()
                .studentNum(dataDetailDto.getStudentNum())
                .score(dataDetailDto.getScore())
                .comment(dataDetailDto.getComment())
                .tag(tag)
                .build();
    }

    public void update(DataDetailDto dataDetailDto) {
        this.studentNum = dataDetailDto.getStudentNum();
        this.score = dataDetailDto.getScore();
        this.comment = dataDetailDto.getComment();
    }

    public void updateScore(DataDetailDto dataDetailDto) {
        this.score = dataDetailDto.getScore();
    }
}
