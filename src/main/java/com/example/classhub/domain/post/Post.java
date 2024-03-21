package com.example.classhub.domain.post;

import com.example.classhub.domain.BaseEntity;
import com.example.classhub.domain.lectureroom.LectureRoom;
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
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String postTitle;

    @Column(nullable = false)
    private String postContent;

    @Column(nullable = false)
    private String postShareRange;

    @Column(nullable = true)
    private String tagId;

//    private Long lRoomId;

    @ManyToOne
    @JoinColumn(name = "lectureRoomId")
    private LectureRoom lectureRoom;


    public static Post from(PostDto postDto, LectureRoom lectureRoom) {
        return Post.builder()
                .postTitle(postDto.getPostTitle())
                .postContent(postDto.getPostContent())
                .postShareRange(postDto.getPostShareRange())
                .tagId(postDto.getTagId())
                .lectureRoom(lectureRoom)
                .build();
    }

    public void update(PostDto from) {
        this.postTitle = from.getPostTitle();
        this.postContent = from.getPostContent();
        this.postShareRange = from.getPostShareRange();
        this.tagId = from.getTagId();
    }
}
