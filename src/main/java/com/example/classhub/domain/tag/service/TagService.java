package com.example.classhub.domain.tag.service;


import com.example.classhub.domain.classhub_lroom.ClassHub_LRoom;
import com.example.classhub.domain.classhub_lroom.dto.LectureRoomDto;
import com.example.classhub.domain.classhub_lroom.repository.LectureRoomRepository;
import com.example.classhub.domain.classhub_lroom.service.LectureRoomService;
import com.example.classhub.domain.tag.ClassHub_Tag;
import com.example.classhub.domain.tag.controller.response.TagListResponse;
import com.example.classhub.domain.tag.controller.response.TagResponse;
import com.example.classhub.domain.tag.dto.TagDto;
import com.example.classhub.domain.tag.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final LectureRoomRepository lectureRoomRepository;

    @Transactional
    public TagDto createTag(TagDto tagDto, Long lRoomId){
        ClassHub_LRoom lRoom = lectureRoomRepository.findById(lRoomId).get();
        // ClassHub_Tag 엔티티 생성 및 저장
        ClassHub_Tag tag = ClassHub_Tag.from(tagDto, lRoom);
        tagRepository.save(tag);

        return TagDto.from(tag);
    }
    @Transactional
    public TagListResponse getTagList() {
        List<ClassHub_Tag> tags = tagRepository.findAll();
        List<TagResponse> tagResponses = tags.stream()
                .map(TagResponse::new)
                .collect(Collectors.toList());
        return new TagListResponse(tagResponses);
    }
    @Transactional
    public TagListResponse getTagListByLectureId(Long lectureId) {
        ClassHub_LRoom lectureRoom = lectureRoomRepository.findById(lectureId).orElse(null);
        if (lectureRoom == null) {
            return new TagListResponse(Collections.emptyList());
        }
        List<ClassHub_Tag> tags = tagRepository.findByLectureRoom(lectureRoom);
        List<TagResponse> tagResponses = tags.stream()
                .map(TagResponse::new)
                .collect(Collectors.toList());
        return new TagListResponse(tagResponses);
    }
    @Transactional
    public TagDto findByTagId(Long tagId){
        ClassHub_Tag tag = tagRepository.findById(tagId)
                .orElseThrow(()-> new IllegalArgumentException("해당 태그가 존재하지 않습니다."));
        return TagDto.from(tag);
    }
    @Transactional
    public TagDto update(Long tagId, TagDto tagDto){
        ClassHub_Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException("해당 태그가 존재하지 않습니다."));

        tag.update(tagDto);
        tagRepository.save(tag);
        return tagDto.from(tag);
    }
    @Transactional
    public void tagDelete(Long tagId){tagRepository.deleteById(tagId);}

    public TagDto findByName(String name) {
        ClassHub_Tag tag = tagRepository.findByName(name);
        return TagDto.from(tag);
    }
}
