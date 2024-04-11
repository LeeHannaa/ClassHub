package com.example.classhub.domain.post.controller.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class PostCheckRequest {
    private List<String> tagNames;
    private List<Long> isSelected;
    private List<Long> isScore;
    private Long keyId;
    private List<Long> isCover; // 덮어쓰기 여부
}
