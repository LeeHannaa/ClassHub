package com.example.classhub.domain.post.dto;

import com.example.classhub.domain.post.ClassHub_Post;
import com.example.classhub.domain.post.controller.request.PostCheckRequest;
import com.example.classhub.domain.post.controller.request.PostCreateRequest;
import com.example.classhub.domain.post.controller.request.PostUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<Boolean> isSelected;
    private List<Boolean> isScore;

    public static PostDto from(PostCreateRequest postCreateRequest) {
        return PostDto.builder()
                .postTitle(postCreateRequest.getPostTitle())
                .postContent(postCreateRequest.getPostContent())
                .postShareRange(postCreateRequest.getPostShareRange())
                .lRoomId(postCreateRequest.getLRoomId())
                .build();
    }
    public static PostDto from(PostCheckRequest postCheckRequest) {
        return PostDto.builder()
                .isSelected(postCheckRequest.getIsSelected())
                .isScore(postCheckRequest.getIsScore())
                .build();
    }

    public static PostDto from(PostCreateRequest postCreateRequest, PostCheckRequest postCheckRequest){
        return PostDto.builder()
                .postTitle(postCreateRequest.getPostTitle())
                .postContent(postCreateRequest.getPostContent())
                .postShareRange(postCreateRequest.getPostShareRange())
                .lRoomId(postCreateRequest.getLRoomId())
                .isSelected(postCheckRequest.getIsSelected())
                .isScore(postCheckRequest.getIsScore())
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

    public static PostDto from(ClassHub_Post post){
        return PostDto.builder()
                .id(post.getPostId())
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .postShareRange(post.getPostShareRange())
                .tagId(post.getTagId())
                .lRoomId(post.getLRoom().getLRoomId())
                .build();
    }
}
