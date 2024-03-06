package com.example.classhub.domain.tag.controller.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagListResponse {
    private List<TagResponse> tags;

    public TagListResponse(List<TagResponse> tags){
        this.tags = tags;
    }
}
