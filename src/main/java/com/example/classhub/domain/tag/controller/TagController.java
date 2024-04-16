package com.example.classhub.domain.tag.controller;

import com.example.classhub.domain.tag.controller.request.TagRequest;
import com.example.classhub.domain.tag.controller.response.TagListResponse;
import com.example.classhub.domain.tag.controller.response.TagResponse;
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
    public String createTag(TagRequest request){
        tagService.createTag(TagDto.from(request), request.getLRoomId());
        return "redirect:/tag";
    }

    @GetMapping("/tag")
    public String findTagList(Model model){
        TagListResponse tagListResponse = tagService.getTagList();
        model.addAttribute("tags", tagListResponse.getTags());
        return "index";
    }

    @GetMapping("/tag/updateForm/{tagId}")
    public String updateTagForm(@PathVariable Long tagId, Model model){
        TagDto tagDto = tagService.findByTagId(tagId);
        model.addAttribute("tag", tagDto);
        return "tagUpdate";
    }

    @PostMapping("/tag/update/{tagId}")
    public void updateTag(@PathVariable Long tagId, @ModelAttribute("tag") TagRequest request){
        tagService.update(tagId, TagDto.from(request));
    }

    //ToDo: tag 삭제 외래키 해결해야함
    @PostMapping("/tag/delete/{tagId}")
    public String tagDelete(@PathVariable(value = "tagId") Long tagId){
        tagService.tagDelete(tagId);
        return "redirect:/lectureRoom/lectureRoomInfo";
    }
}
