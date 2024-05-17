package com.example.classhub.domain.post.controller.response;

import com.example.classhub.domain.post.dto.PostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class PostListResponse {
    private List<PostDto> posts;
    private int totalPages;
    private int currentPage;

    public PostListResponse(List<PostDto> posts, int totalPages, int currentPage) {
        this.posts = posts;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }
}
