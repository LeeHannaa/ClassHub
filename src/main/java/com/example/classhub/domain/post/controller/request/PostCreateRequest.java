package com.example.classhub.domain.post.controller.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;

@Getter
@NoArgsConstructor
@Data
public class PostCreateRequest {
    private String postTitle;
    private String postContent;
    private Long lRoomId;
}
