package com.example.classhub.domain.post.service;


import com.example.classhub.domain.classhub_lroom.ClassHub_LRoom;
import com.example.classhub.domain.classhub_lroom.repository.LectureRoomRepository;
import com.example.classhub.domain.datadetail.service.DataDetailService;
import com.example.classhub.domain.filedata.dto.FileDataDto;
import com.example.classhub.domain.filedata.service.FileDataService;
import com.example.classhub.domain.post.ClassHub_Post;
import com.example.classhub.domain.post.controller.response.PostListResponse;
import com.example.classhub.domain.post.controller.response.PostResponse;
import com.example.classhub.domain.post.dto.PostDto;
import com.example.classhub.domain.post.repository.PostRepository;
import com.example.classhub.domain.tag.ClassHub_Tag;
import com.example.classhub.domain.tag.dto.TagDto;
import com.example.classhub.domain.tag.service.TagService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.input.BOMInputStream;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final LectureRoomRepository lectureRoomRepository;
    private final FileDataService fileDataService;
    private final TagService tagService;

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

    public String processCsvFile(ClassHub_LRoom lRoom, MultipartFile csvFile) {
        StringBuilder tagIdsBuilder = new StringBuilder();
        try (BOMInputStream bomInputStream = new BOMInputStream(csvFile.getInputStream());
             BufferedReader fileReader = new BufferedReader(new InputStreamReader(bomInputStream, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            Map<String, Integer> headers = csvParser.getHeaderMap();
            for (String headerName : headers.keySet()) {
                ClassHub_Tag tag = ClassHub_Tag.builder()
                        .name(headerName)
                        .lectureRoom(lRoom)
                        .build();
                TagDto createdTagDto = tagService.createTag(TagDto.from(tag), lRoom.getLRoomId()); // 생성된 태그 정보 가져오기
                Long tagId = createdTagDto.getTagId(); // 생성된 태그의 아이디 가져오기
                tagIdsBuilder.append(tagId).append(",");
            }
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
        String tagIds = tagIdsBuilder.toString();
        if (tagIds.endsWith(",")) {
            tagIds = tagIds.substring(0, tagIds.length() - 1); // 마지막 쉼표 제거
        }
        return tagIds;
    }

    @Transactional
    public void savePost(PostDto postDto, MultipartFile file) {
        ClassHub_LRoom lRoom = lectureRoomRepository.findById(postDto.getLRoomId())
                .orElseThrow(() -> new IllegalArgumentException("해당 강의실이 존재하지 않습니다."));

        if (!file.isEmpty()) {
            String tagIds = processCsvFile(lRoom, file);
            ClassHub_Post post = ClassHub_Post.from(postDto, lRoom, tagIds);
            postRepository.save(post);

            FileDataDto fileDataDto = FileDataDto.builder()
                    .fileDataName(getFileDataName(file))
                    .fileDataType(getFileDataType(file))
                    .postId(post.getPostId())
                    .build();
            fileDataService.saveFileData(fileDataDto);
        } else {
            ClassHub_Post post = ClassHub_Post.from(postDto, lRoom);
            postRepository.save(post);
        }
    }
    @Transactional
    public PostListResponse getPostList(){
        List<ClassHub_Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = posts.stream()
                .map(PostResponse::new)
                .toList();
        return new PostListResponse(postResponses);
   }
    @Transactional
    public PostDto findByPostId(Long postId) {
        ClassHub_Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        return PostDto.from(post);
    }
    @Transactional
    public void update(Long postId, PostDto from) {
        ClassHub_Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        post.update(from);
        postRepository.save(post);
    }
    @Transactional
    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }
}
