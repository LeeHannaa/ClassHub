package com.example.classhub.domain.post.controller.response;

import com.example.classhub.domain.post.Post;
import com.example.classhub.domain.post.dto.PostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class PostResponse {
    private Long id;
    private String postTitle;
    private String postContent;
    private String postShareRange;
    private String tagId;
    private Long lRoomId;

    public PostResponse(Post post){
        this.id = post.getId();
        this.postTitle = post.getPostTitle();
        this.postContent = post.getPostContent();
        this.postShareRange = post.getPostShareRange();
        this.tagId = post.getTagId();
        this.lRoomId = post.getLectureRoom().getLectureRoomId();
    }
    public PostResponse(PostDto postDto){
        this.id = postDto.getId();
        this.postTitle = postDto.getPostTitle();
        this.postContent = postDto.getPostContent();
        this.postShareRange = postDto.getPostShareRange();
        this.tagId = postDto.getTagId();
        this.lRoomId = postDto.getLRoomId();
    }
}
