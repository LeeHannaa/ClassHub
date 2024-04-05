package com.example.classhub.domain.post.controller.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class PostCheckRequest {
    private List<String> tagNames;
    private List<Boolean> isSelected;
    private List<Boolean> isScore;
}
