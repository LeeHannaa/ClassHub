package com.example.classhub.domain.tag.controller.response;

import com.example.classhub.domain.tag.Tag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagResponse {
    private Long tagId;
    private String name;

    public TagResponse(Tag tag){
        this.tagId = tag.getTagId();
        this.name = tag.getName();
    }
}
