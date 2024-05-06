package com.example.classhub.domain.tag.controller.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class TagRequest {
    private String name;
    private Long lRoomId;
    private boolean nan;
    private int perfectScore;
}
