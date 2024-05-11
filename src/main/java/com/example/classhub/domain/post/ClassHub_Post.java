package com.example.classhub.domain.post;

import com.example.classhub.domain.BaseEntity;
import com.example.classhub.domain.classhub_lroom.ClassHub_LRoom;
import com.example.classhub.domain.post.dto.PostDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassHub_Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private String postTitle;

    @Column(nullable = false)
    private String postContent;

    @Column(nullable = true)
    private String tagId;

    @ManyToOne(targetEntity = ClassHub_LRoom.class)
    @JoinColumn(name = "lRoomId" )
    private ClassHub_LRoom lRoom;


    public static ClassHub_Post from(PostDto postDto, ClassHub_LRoom lRoom) {
        return ClassHub_Post.builder()
                .postTitle(postDto.getPostTitle())
                .postContent(postDto.getPostContent())
                .tagId("1")
                .lRoom(lRoom)
                .build();
    }

    public static ClassHub_Post from(PostDto postDto, ClassHub_LRoom lRoom, String tagId) {
        return ClassHub_Post.builder()
                .postTitle(postDto.getPostTitle())
                .postContent(postDto.getPostContent())
                .tagId(tagId)
                .lRoom(lRoom)
                .build();
    }

    public void update(PostDto postDto) {
        this.postTitle = postDto.getPostTitle();
        this.postContent = postDto.getPostContent();
    }
}
