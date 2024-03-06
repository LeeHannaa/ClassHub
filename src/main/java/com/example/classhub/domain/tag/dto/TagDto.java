package com.example.classhub.domain.tag.dto;

import com.example.classhub.domain.tag.Tag;
import com.example.classhub.domain.tag.controller.request.TagRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class TagDto {
    private Long tagId;
    private String name;

    public static TagDto from(TagRequest request){
        return TagDto.builder()
                .name(request.getName())
                .build();
    }

    public static TagDto from(Tag tag){
        return TagDto.builder()
                .tagId(tag.getTagId())
                .name(tag.getName())
                .build();
    }
}
