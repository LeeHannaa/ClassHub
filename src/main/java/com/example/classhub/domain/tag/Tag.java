package com.example.classhub.domain.tag;

import com.example.classhub.domain.BaseEntity;
import com.example.classhub.domain.tag.dto.TagDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    private String name;

    public static Tag from(TagDto tagDto) {
        return Tag.builder()
                .name(tagDto.getName())
                .build();
    }

    public void update(TagDto tagDto) {
        this.name = tagDto.getName();
    }
}

