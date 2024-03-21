package com.example.classhub.domain.post.controller.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class PostCreateRequest {
    private String postTitle;
    private String postContent;
    private String postShareRange;
    private String tagId;
    private Long lRoomId;
}
