package com.example.classhub.domain.tag.service;


import com.example.classhub.domain.tag.Tag;
import com.example.classhub.domain.tag.dto.TagDto;
import com.example.classhub.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public TagDto createTag(TagDto tagDto){
        Tag tag = Tag.from(tagDto);
        tagRepository.save(tag);

        return TagDto.from(tag);
    }
}
