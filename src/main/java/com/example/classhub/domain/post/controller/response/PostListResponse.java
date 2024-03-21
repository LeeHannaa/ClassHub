package com.example.classhub.domain.post.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class PostListResponse {
    private List<PostResponse> posts;

    public PostListResponse(List<PostResponse> posts){
        this.posts = posts;
    }
}
