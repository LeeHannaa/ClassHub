package com.example.classhub.domain.post.controller;

import com.example.classhub.domain.datadetail.dto.DataDetailDto;
import com.example.classhub.domain.datadetail.service.DataDetailService;
import com.example.classhub.domain.post.controller.request.PostCreateRequest;
import com.example.classhub.domain.post.controller.response.PostListResponse;
import com.example.classhub.domain.post.dto.PostDto;
import com.example.classhub.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@CrossOrigin("*")
public class PostController {
    private final PostService postService;
    private final DataDetailService dataDetailService;

    @GetMapping("/post/postForm")
    public String createPostForm(Model model){
        model.addAttribute("post", new PostCreateRequest());
        return "postForm";
    }
    @PostMapping("/post/postForm")
    public String postForm(@ModelAttribute("postForm") PostCreateRequest request, @RequestParam("file") MultipartFile file){
        postService.savePost(PostDto.from(request), file);
//        dataDetailService.saveDataDetail(DataDetailDto.from(file));
        return "redirect:/post/postList";
    }

    @GetMapping("/post/postList")
    public String findPostList(Model model){
        PostListResponse postListResponse = postService.getPostList();
        model.addAttribute("posts", postListResponse.getPosts());
        return "postList";
    }

    @GetMapping("/post/updateForm/{postId}")
    public String updateForm(@ModelAttribute("postId") Long postId, Model model){
        PostDto postDto = postService.findByPostId(postId);
        model.addAttribute("post", postDto);
        return "postUpdateForm";
    }
    @PostMapping("/post/updateForm/{postId}")
    public String update(@ModelAttribute("postId") Long postId, @ModelAttribute("post") PostCreateRequest request){
        postService.update(postId, PostDto.from(request));
        return "redirect:/post/postList";
    }

    @GetMapping("/post/delete/{postId}")
    public String delete(@ModelAttribute("postId") Long postId){
        postService.delete(postId);
        return "redirect:/post/postList";
    }
}
