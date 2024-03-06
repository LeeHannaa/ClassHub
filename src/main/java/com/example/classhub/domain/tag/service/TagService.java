package com.example.classhub.domain.tag.service;


import com.example.classhub.domain.tag.Tag;
import com.example.classhub.domain.tag.controller.response.TagListResponse;
import com.example.classhub.domain.tag.controller.response.TagResponse;
import com.example.classhub.domain.tag.dto.TagDto;
import com.example.classhub.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public TagDto createTag(TagDto tagDto){
        Tag tag = Tag.from(tagDto);
        tagRepository.save(tag);

        return TagDto.from(tag);
    }

    public TagListResponse getTagList() {
        List<Tag> tags = tagRepository.findAll();
        List<TagResponse> tagResponses = tags.stream()
                .map(TagResponse::new)
                .collect(Collectors.toList());
        return new TagListResponse(tagResponses);
    }

    public void tagDelete(Long tagId){tagRepository.deleteById(tagId);}
}
