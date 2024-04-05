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
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
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
    private final DataDetailService dataDetailService;

    public List<String> checkHeader(File file) { // 수정된 부분: MultipartFile 대신 File 사용
        List<CSVRecord> records = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file);
             BOMInputStream bomInputStream = new BOMInputStream(fis, false); // BOMInputStream을 사용하여 BOM을 처리
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bomInputStream, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(bufferedReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            for (CSVRecord record : csvParser) {
                records.add(record);
            }
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
        return new ArrayList<>(records.get(0).toMap().keySet());
    }

    @Transactional
    public String saveTag(ClassHub_LRoom lRoom, File csvFile, List<Boolean> isSelected, List<Boolean> isScore) throws IOException {
        StringBuilder tagIdsBuilder = new StringBuilder();
        List<CSVRecord> records = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(csvFile);
             BOMInputStream bomInputStream = new BOMInputStream(fis, false); // BOMInputStream을 사용하여 BOM을 처리
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bomInputStream, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(bufferedReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            records.addAll(csvParser.getRecords());
            Map<String, Integer> headers = csvParser.getHeaderMap();
            List<String> headerNames = new ArrayList<>(headers.keySet());

            for (int i = 0; i < headerNames.size(); i++) {
                if (!isSelected.get(i)) continue;

                String headerName = headerNames.get(i);
                boolean isScoreTag = isScore.get(i);

                TagDto tagDto = TagDto.builder()
                        .name(headerName)
                        .lRoomId(lRoom.getLRoomId())
                        .nan(!isScoreTag)
                        .build();
                TagDto createdTagDto = tagService.createTag(tagDto, lRoom.getLRoomId());

                // 각 레코드에 대해 처리합니다.
                for (CSVRecord record : records) {
                    if (isScoreTag) {
                        // 점수 태그인 경우
                        DataDetailDto dataDetail = DataDetailDto.builder()
                                .studentNum(record.get("학번"))
                                .score(Double.parseDouble(record.get(headerName)))
                                .tagId(createdTagDto.getTagId())
                                .build();
                        dataDetailService.saveDataDetail(dataDetail);
                    } else {
                        // 코멘트 태그인 경우
                        DataDetailDto dataDetail = DataDetailDto.builder()
                                .studentNum(record.get("학번"))
                                .comment(record.get(headerName))
                                .tagId(createdTagDto.getTagId())
                                .build();
                        dataDetailService.saveDataDetail(dataDetail);
                    }
                }

                Long tagId = createdTagDto.getTagId();
                tagIdsBuilder.append(tagId).append(",");
            }
        }

        String tagIds = tagIdsBuilder.toString();
        if (tagIds.endsWith(",")) {
            tagIds = tagIds.substring(0, tagIds.length() - 1);
        }
        return tagIds;
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
            String tagIds = saveTag(lRoom, file, postDto.getIsSelected(), postDto.getIsScore());

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
