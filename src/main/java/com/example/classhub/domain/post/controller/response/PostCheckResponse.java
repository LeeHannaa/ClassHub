package com.example.classhub.domain.post.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class PostCheckResponse {
    private List<String> tagNames;
    private List<Long> isSelected;
    private List<Long> isScore;
    private Long keyId;
    private List<Long> isCover; // 덮어쓰기 여부

    public PostCheckResponse(List<String> tagNames, List<Long> isSelected, List<Long> isScore, Long keyId, List<Long> isCover){
        this.tagNames = tagNames;
        this.isSelected = isSelected;
        this.isScore = isScore;
        this.keyId = keyId;
        this.isCover = isCover;
    }
}
