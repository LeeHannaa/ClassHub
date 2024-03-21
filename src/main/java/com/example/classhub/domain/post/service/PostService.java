package com.example.classhub.domain.post.service;

import com.example.classhub.domain.lectureroom.LectureRoom;
import com.example.classhub.domain.lectureroom.repository.LectureRoomRepository;
import com.example.classhub.domain.post.Post;
import com.example.classhub.domain.post.controller.response.PostListResponse;
import com.example.classhub.domain.post.controller.response.PostResponse;
import com.example.classhub.domain.post.dto.PostDto;
import com.example.classhub.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final LectureRoomRepository lectureRoomRepository;

    public void savePost(PostDto postDto) {
        LectureRoom lectureRoom = lectureRoomRepository.findById(postDto.getLRoomId()).orElseThrow(() -> new IllegalArgumentException("해당 강의실이 존재하지 않습니다."));
        Post post = Post.from(postDto, lectureRoom);
        postRepository.save(post);

        PostDto.from(post);
    }

   public PostListResponse getPostList(){
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = posts.stream()
                .map(PostResponse::new)
                .toList();
        return new PostListResponse(postResponses);
   }

    public PostDto findByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        return PostDto.from(post);
    }

    public void update(Long postId, PostDto from) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        post.update(from);
        postRepository.save(post);
    }

    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }
}
