package com.example.classhub.domain.post.service;


import com.example.classhub.domain.classhub_lroom.ClassHub_LRoom;
import com.example.classhub.domain.classhub_lroom.repository.LectureRoomRepository;
import com.example.classhub.domain.filedata.dto.FileDataDto;
import com.example.classhub.domain.filedata.service.FileDataService;
import com.example.classhub.domain.post.ClassHub_Post;
import com.example.classhub.domain.post.controller.response.PostListResponse;
import com.example.classhub.domain.post.controller.response.PostResponse;
import com.example.classhub.domain.post.dto.PostDto;
import com.example.classhub.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final LectureRoomRepository lectureRoomRepository;
    private final FileDataService fileDataService;

    public String getFileDataName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String[] filenameParts = originalFilename.split("\\.");
        return filenameParts[0]; // 파일 이름
    }

    public String getFileDataType(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String[] filenameParts = originalFilename.split("\\.");
        return filenameParts.length > 1 ? filenameParts[filenameParts.length - 1] : ""; // 확장자
    }

    public void savePost(PostDto postDto, MultipartFile file) {
        ClassHub_LRoom lRoom = lectureRoomRepository.findById(postDto.getLRoomId()).orElseThrow(() -> new IllegalArgumentException("해당 강의실이 존재하지 않습니다."));
        ClassHub_Post post = ClassHub_Post.from(postDto, lRoom);
        postRepository.save(post);

        if (!file.isEmpty()) { // 파일이 비어있지 않은 경우에만 처리
            FileDataDto fileDataDto = FileDataDto.builder()
                    .fileDataName(getFileDataName(file))
                    .fileDataType(getFileDataType(file))
                    .postId(post.getPostId()) // 저장된 게시글의 ID 설정
                    .build();

            fileDataService.saveFileData(fileDataDto);
        }
    }

   public PostListResponse getPostList(){
        List<ClassHub_Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = posts.stream()
                .map(PostResponse::new)
                .toList();
        return new PostListResponse(postResponses);
   }

    public PostDto findByPostId(Long postId) {
        ClassHub_Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        return PostDto.from(post);
    }

    public void update(Long postId, PostDto from) {
        ClassHub_Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        post.update(from);
        postRepository.save(post);
    }

    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }
}
