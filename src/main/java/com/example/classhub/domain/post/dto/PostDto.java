package com.example.classhub.domain.post.dto;

import com.example.classhub.domain.post.ClassHub_Post;
import com.example.classhub.domain.post.controller.request.PostCheckRequest;
import com.example.classhub.domain.post.controller.request.PostCreateRequest;
import com.example.classhub.domain.post.controller.request.PostUpdateRequest;
import com.example.classhub.domain.tag.service.TagService;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PostDto {
    private Long id;
    private String postTitle;
    private String postContent;
    private String tagId;
    private Long lRoomId;
    private Long keyId;
    private List<Long> isSelected;
    private List<Long> isScore;
    private List<Long> isCover;
    private String tagNames;
    private String regDate;
    private String modDate;

    public static PostDto from(PostCreateRequest postCreateRequest) {
        return PostDto.builder()
                .postTitle(postCreateRequest.getPostTitle())
                .postContent(postCreateRequest.getPostContent())
                .lRoomId(postCreateRequest.getLRoomId())
                .build();
    }
    public static PostDto from(PostCheckRequest postCheckRequest) {
        return PostDto.builder()
                .keyId(postCheckRequest.getKeyId())
                .isSelected(postCheckRequest.getIsSelected())
                .isScore(postCheckRequest.getIsScore())
                .isCover(postCheckRequest.getIsCover())
                .build();
    }

    public static PostDto from(PostCreateRequest postCreateRequest, PostCheckRequest postCheckRequest){
        return PostDto.builder()
                .postTitle(postCreateRequest.getPostTitle())
                .postContent(postCreateRequest.getPostContent())
                .lRoomId(postCreateRequest.getLRoomId())
                .keyId(postCheckRequest.getKeyId())
                .isSelected(postCheckRequest.getIsSelected())
                .isScore(postCheckRequest.getIsScore())
                .isCover(postCheckRequest.getIsCover())
                .build();
    }

    public static PostDto from(PostUpdateRequest postUpdateRequest) {
        return PostDto.builder()
                .postTitle(postUpdateRequest.getPostTitle())
                .postContent(postUpdateRequest.getPostContent())
                .build();
    }

    public static PostDto from(ClassHub_Post post){
        return PostDto.builder()
                .id(post.getPostId())
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .tagId(post.getTagId())
                .lRoomId(post.getLRoom().getLRoomId())
                .build();
    }

    public static PostDto from(ClassHub_Post post, TagService tagService){
        String tagNames = tagService.getTagNamesByIds(post.getTagId());
        System.out.println("tagNames: " + tagNames);
        return PostDto.builder()
                .id(post.getPostId())
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .tagId(post.getTagId())
                .tagNames(tagNames)
                .lRoomId(post.getLRoom().getLRoomId())
                .regDate(post.getRegDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .modDate(post.getModDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .build();
    }
}
