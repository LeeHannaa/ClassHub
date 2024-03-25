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

    public void processCsvFile(ClassHub_LRoom lRoom, MultipartFile csvFile) {
        try (BOMInputStream bomInputStream = new BOMInputStream(csvFile.getInputStream());
             BufferedReader fileReader = new BufferedReader(new InputStreamReader(bomInputStream, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            Map<String, Integer> headers = csvParser.getHeaderMap();
            for (String headerName : headers.keySet()) {
                ClassHub_Tag tag = ClassHub_Tag.builder()
                        .name(headerName)
                        .lectureRoom(lRoom)
                        .build();
                tagService.createTag(TagDto.from(tag), lRoom.getLRoomId());
            }
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public void savePost(PostDto postDto, MultipartFile file) {
        ClassHub_LRoom lRoom = lectureRoomRepository.findById(postDto.getLRoomId()).orElseThrow(() -> new IllegalArgumentException("해당 강의실이 존재하지 않습니다."));
        ClassHub_Post post = ClassHub_Post.from(postDto, lRoom);
        postRepository.save(post);

        if (!file.isEmpty()) {
            FileDataDto fileDataDto = FileDataDto.builder()
                    .fileDataName(getFileDataName(file))
                    .fileDataType(getFileDataType(file))
                    .postId(post.getPostId())
                    .build();
            fileDataService.saveFileData(fileDataDto);
            processCsvFile(lRoom, file);
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
