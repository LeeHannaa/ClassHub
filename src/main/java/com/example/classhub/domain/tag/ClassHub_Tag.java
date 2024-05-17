package com.example.classhub.domain.tag;

import com.example.classhub.domain.BaseEntity;
import com.example.classhub.domain.classhub_lroom.ClassHub_LRoom;
import com.example.classhub.domain.tag.dto.TagDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE class_hub_tag SET deleted = true WHERE tag_id = ?")
@Where(clause = "deleted = false")
public class ClassHub_Tag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    private String name;
    private boolean nan;
    @Setter
    private int perfectScore = 100; // 만점 칼럼 : 기본값 100

    // 소프트 딜리트
    private boolean deleted = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "lRoomId")
    private ClassHub_LRoom lectureRoom;

    public static ClassHub_Tag from(TagDto tagDto, ClassHub_LRoom lectureRoom, String newName) {
        return ClassHub_Tag.builder()
                .name(newName)
                .nan(tagDto.isNan())
                .lectureRoom(lectureRoom)
                .perfectScore(tagDto.getPerfectScore())
                .deleted(false)
                .build();
    }

    public static ClassHub_Tag from(TagDto tagDto) {
        return ClassHub_Tag.builder()
                .name(tagDto.getName())
                .nan(tagDto.isNan())
                .build();
    }

    public void update(TagDto tagDto) {
        this.name = tagDto.getName();
    }

}

