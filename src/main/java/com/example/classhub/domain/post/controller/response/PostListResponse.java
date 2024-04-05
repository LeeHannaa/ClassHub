package com.example.classhub.domain.post.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class PostListResponse {
    private List<PostResponse> posts;
    private int totalPages;
    private int currentPage;

    // 페이징 처리된 응답을 위한 생성자 추가
    public PostListResponse(List<PostResponse> posts, int totalPages, int currentPage){
      this.posts = posts;
      this.totalPages = totalPages;
      this.currentPage = currentPage;
    }
    public PostListResponse(List<PostResponse> posts){
        this.posts = posts;
    }
}
