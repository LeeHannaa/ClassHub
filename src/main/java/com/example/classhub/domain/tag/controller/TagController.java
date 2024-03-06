package com.example.classhub.domain.tag.controller;

import com.example.classhub.domain.tag.controller.request.TagRequest;
import com.example.classhub.domain.tag.controller.response.TagListResponse;
import com.example.classhub.domain.tag.dto.TagDto;
import com.example.classhub.domain.tag.service.TagService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        return "redirect:/tag";
    }

    @GetMapping("/tag")
    public String findTagList(Model model){
        TagListResponse tagListResponse = tagService.getTagList();
        model.addAttribute("tags", tagListResponse.getTags());
        return "index";
    }

    @GetMapping("/tag/delete/{tagId}")
    public String tagDelete(@PathVariable(value = "tagId") Long tagId){
        tagService.tagDelete(tagId);
        return "redirect:/tag";
    }
}
