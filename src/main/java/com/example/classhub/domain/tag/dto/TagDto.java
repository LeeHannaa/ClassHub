package com.example.classhub.domain.tag.dto;

import com.example.classhub.domain.classhub_lroom.ClassHub_LRoom;
import com.example.classhub.domain.tag.ClassHub_Tag;
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
    private Long lRoomId;
    private boolean nan;

    public static TagDto from(TagRequest request){
        return TagDto.builder()
                .name(request.getName())
                .lRoomId(request.getLRoomId())
                .nan(request.isNan())
                .build();
    }

    public static TagDto from(ClassHub_Tag tag){
        return TagDto.builder()
                .tagId(tag.getTagId())
                .name(tag.getName())
                .lRoomId(tag.getLectureRoom().getLRoomId())
                .nan(tag.isNan())
                .build();
    }
}
