package com.example.classhub.domain.post.dto;

import com.example.classhub.domain.post.Post;
import com.example.classhub.domain.post.controller.request.PostCreateRequest;
import com.example.classhub.domain.post.controller.request.PostUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PostDto {
    private Long id;
    private String postTitle;
    private String postContent;
    private String postShareRange;
    private String tagId;
    private Long lRoomId;

    public static PostDto from(PostCreateRequest postCreateRequest) {
        return PostDto.builder()
                .postTitle(postCreateRequest.getPostTitle())
                .postContent(postCreateRequest.getPostContent())
                .postShareRange(postCreateRequest.getPostShareRange())
                .tagId(postCreateRequest.getTagId())
                .lRoomId(postCreateRequest.getLRoomId())
                .build();
    }

    public static PostDto from(PostUpdateRequest postUpdateRequest) {
        return PostDto.builder()
                .postTitle(postUpdateRequest.getPostTitle())
                .postContent(postUpdateRequest.getPostContent())
                .postShareRange(postUpdateRequest.getPostShareRange())
                .tagId(postUpdateRequest.getTagId())
                .build();
    }

    public static PostDto from(Post post){
        return PostDto.builder()
                .id(post.getId())
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .postShareRange(post.getPostShareRange())
                .tagId(post.getTagId())
                .lRoomId(post.getLRoomId())
                .build();
    }
}
