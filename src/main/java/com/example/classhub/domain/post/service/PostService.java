package com.example.classhub.domain.post.service;


import com.example.classhub.domain.classhub_lroom.ClassHub_LRoom;
import com.example.classhub.domain.classhub_lroom.repository.LectureRoomRepository;
import com.example.classhub.domain.datadetail.dto.DataDetailDto;
import com.example.classhub.domain.datadetail.service.DataDetailService;
import com.example.classhub.domain.filedata.dto.FileDataDto;
import com.example.classhub.domain.filedata.service.FileDataService;
import com.example.classhub.domain.post.ClassHub_Post;
import com.example.classhub.domain.post.controller.response.PostListResponse;
import com.example.classhub.domain.post.controller.response.PostResponse;
import com.example.classhub.domain.post.dto.PostDto;
import com.example.classhub.domain.post.repository.PostRepository;
import com.example.classhub.domain.tag.dto.TagDto;
import com.example.classhub.domain.tag.service.TagService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.io.input.BOMInputStream;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final LectureRoomRepository lectureRoomRepository;
    private final FileDataService fileDataService;
    private final TagService tagService;
    private final DataDetailService dataDetailService;

    public List<String> checkHeader(File file) {
        List<String> headers;
        try (FileInputStream fis = new FileInputStream(file);
             BOMInputStream bomInputStream = new BOMInputStream(fis, false);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bomInputStream, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(bufferedReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            headers = csvParser.getHeaderNames();
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
        return headers;
    }

    @Transactional
    public String saveTag(ClassHub_LRoom lRoom, File csvFile, PostDto postDto) throws IOException {
        List<String> headers = checkHeader(csvFile);
        StringBuilder tagIdsBuilder = new StringBuilder();
        List<CSVRecord> records = readCsvRecords(csvFile);
        String keyHeaderName = lRoom.getStudentInfoKey();
        for (Long selectedId : postDto.getIsSelected()) {
            String headerName = headers.get(selectedId.intValue());
            boolean isScoreTag = postDto.getIsScore().contains(selectedId);
            boolean isCover = postDto.getIsCover() != null && postDto.getIsCover().contains(selectedId);

            TagDto tagDto = TagDto.builder()
                    .name(headerName)
                    .lRoomId(lRoom.getLRoomId())
                    .nan(!isScoreTag)
                    .build();
            TagDto createdTagDto = tagService.createOrUpdateTag(tagDto, isCover, lRoom.getLRoomId());

            for (CSVRecord record : records) {
                String keyIdValue = record.get(keyHeaderName);
                saveDataDetail(record, createdTagDto, headerName, keyIdValue, isScoreTag);
            }
            tagIdsBuilder.append(createdTagDto.getTagId()).append(",");
        }
        return trimTrailingComma(tagIdsBuilder.toString());
    }

    private List<CSVRecord> readCsvRecords(File csvFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(csvFile);
             BOMInputStream bomInputStream = new BOMInputStream(fis, false);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bomInputStream, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(bufferedReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            return csvParser.getRecords();
        } catch (IOException e) {
            throw new IOException("CSV 파일을 읽는 중 오류 발생: " + e.getMessage(), e);
        }
    }

    private void saveDataDetail(CSVRecord record, TagDto tagDto, String headerName, String keyIdValue, boolean isScoreTag) {
        DataDetailDto dataDetail;
        if (isScoreTag) {
            double score = Double.parseDouble(record.get(headerName));
            dataDetail = DataDetailDto.builder()
                    .tagId(tagDto.getTagId())
                    .score(score)
                    .studentNum(keyIdValue)
                    .build();
        } else {
            String comment = record.get(headerName);
            dataDetail = DataDetailDto.builder()
                    .tagId(tagDto.getTagId())
                    .comment(comment)
                    .studentNum(keyIdValue)
                    .build();
        }

        dataDetailService.saveDataDetail(dataDetail);
    }

    private String trimTrailingComma(String str) {
        if (str != null && str.endsWith(",")) {
            return str.substring(0, str.length() - 1);
        }
        return str;
    }


    @Transactional
    public void createPost(PostDto postDto, File file) throws IOException {
        ClassHub_LRoom lRoom = lectureRoomRepository.findById(postDto.getLRoomId())
                .orElseThrow(() -> new IllegalArgumentException("해당 강의실이 존재하지 않습니다."));

        if (file.length() == 0) {
            ClassHub_Post post = ClassHub_Post.from(postDto, lRoom);
            System.out.println("Post: " + post);
            postRepository.save(post);
        } else {
            String tagIds = saveTag(lRoom, file, postDto);

            System.out.println("TagIds: " + tagIds);
            ClassHub_Post post = ClassHub_Post.from(postDto, lRoom, tagIds);
            postRepository.save(post);

            String fileDataName = file.getName();
            String fileDataType = Files.probeContentType(file.toPath());

            FileDataDto fileDataDto = FileDataDto.builder()
                    .fileDataName(fileDataName)
                    .fileDataType(fileDataType)
                    .postId(post.getPostId())
                    .build();
            fileDataService.saveFileData(fileDataDto);
        }

        file.delete();
    }

  @Transactional
  public PostListResponse getPostList(int page, int size){
    Pageable pageable = PageRequest.of(page, size, Sort.by("postId").descending());
    Page<ClassHub_Post> posts = postRepository.findAll(pageable);

    List<PostResponse> postResponses = posts.getContent().stream()
      .map(PostResponse::new)
      .collect(Collectors.toList());

    return new PostListResponse(postResponses, posts.getTotalPages(), posts.getNumber());
  }


  @Transactional
    public PostDto findByPostId(Long postId) {
        ClassHub_Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        return PostDto.from(post, tagService);
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
    @Transactional
    public List<PostDto> getPostListByLectureRoomId(Long lRoomId) {
        List<ClassHub_Post> posts = postRepository.findBylRoom_lRoomId(lRoomId);
        return posts.stream()
                .map(post -> PostDto.from(post, tagService))
                .collect(Collectors.toList());
    }
}