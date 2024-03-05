package com.example.classhub.domain.tag.controller;

import com.example.classhub.domain.lectureroom.controller.request.LectureRoomCreateRequest;
import com.example.classhub.domain.lectureroom.dto.LectureRoomDto;
import com.example.classhub.domain.tag.dto.TagDto;
import com.example.classhub.domain.tag.repository.TagRepository;
import com.example.classhub.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping("/tag/tagForm")
    public String createTagForm(Model model){
        model.addAttribute("tag", new TagRequest());
        return "tagForm";
    }
    @PostMapping("/tag/saveTag")
    public String createLectureRoom(@ModelAttribute("lectureRoom") TagRequest request){
        tagService.createTag(TagDto.from(request));
        return "redirect:/lecture-room";
    }
}
