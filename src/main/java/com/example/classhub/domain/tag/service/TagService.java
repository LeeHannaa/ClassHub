package com.example.classhub.domain.tag.service;


import com.example.classhub.domain.classhub_lroom.ClassHub_LRoom;
import com.example.classhub.domain.classhub_lroom.repository.LectureRoomRepository;
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
        String tagName = tagDto.getName();
        String newName = tagName;
        int count = 1;

        while (tagRepository.existsByNameAndLectureRoom_LRoomId(newName, lRoomId)) {
            newName = tagName + "_" + count;
            count++;
        }

        ClassHub_LRoom lRoom = lectureRoomRepository.findById(lRoomId).get();
        ClassHub_Tag tag = ClassHub_Tag.from(tagDto, lRoom, newName);
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
    public Long findLRoomIdByTagId(Long tagId){
        ClassHub_Tag tag = tagRepository.findById(tagId)
                .orElseThrow(()-> new IllegalArgumentException("해당 태그가 존재하지 않습니다."));
        return TagDto.from(tag).getLRoomId();
    }

    @Transactional
    public boolean isTagNameExists(String tagName, Long lRoomId) {
        // 태그 이름으로 데이터베이스에서 태그를 찾음
        ClassHub_Tag tag = tagRepository.findByNameAndLectureRoom_LRoomId(tagName, lRoomId);
        return tag != null;
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

//    public TagDto findByName(String name) {
//        ClassHub_Tag tag = tagRepository.findByName(name);
//        return TagDto.from(tag);
//    }

    @Transactional
    public TagDto createOrUpdateTag(TagDto tagDto, boolean isCover, Long lRoomId) {
        String tagName = tagDto.getName();
        ClassHub_Tag tag = tagRepository.findByNameAndLectureRoom_LRoomId(tagName, lRoomId);

        if (tag == null) {
            if (isCover) {
                System.out.println("isCover true / tag not found, creating new tag.");
                return createTag(tagDto, lRoomId);
            } else {
                throw new IllegalArgumentException("태그를 찾을 수 없습니다. 새 태그를 생성하려면 isCover를 false로 설정해야 합니다.");
            }
        } else {
            if (isCover) {
                tag.update(tagDto);
                tagRepository.save(tag);
                System.out.println("isCover true / updating existing tag: " + tag);
            } else {
                System.out.println("isCover false / tag already exists: " + tag);
                return TagDto.from(tag);
            }
        }
        return TagDto.from(tag);
    }

}
