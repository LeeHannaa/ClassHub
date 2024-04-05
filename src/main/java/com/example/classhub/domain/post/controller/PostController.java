package com.example.classhub.domain.post.controller;

import com.example.classhub.domain.datadetail.service.DataDetailService;
import com.example.classhub.domain.post.controller.request.PostCreateRequest;
import com.example.classhub.domain.post.controller.response.PostListResponse;
import com.example.classhub.domain.post.dto.PostDto;
import com.example.classhub.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@CrossOrigin("*")
public class PostController {
  private final PostService postService;
  private final DataDetailService dataDetailService;

  @GetMapping("/post/postForm")
  public String createPostForm(Model model){
    model.addAttribute("post", new PostCreateRequest());
    return "post/postForm";
  }
  @PostMapping("/post/postForm")
  public String postForm(@ModelAttribute("postForm") PostCreateRequest request, @RequestParam("file") MultipartFile file){
    postService.savePost(PostDto.from(request), file);
//        dataDetailService.saveDataDetail(DataDetailDto.from(file));
    return "redirect:/post";
  }

//  @GetMapping("/post")
//  public String findPostList(Model model, @PageableDefault(size = 10) Pageable pageable){
//    PostListResponse postListResponse = postService.getPostList();
//    model.addAttribute("posts", postListResponse.getPosts());
//    return "post/postList";
//  }

  @GetMapping("/post")
  public String findPostList(Model model,
                             @RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "size", defaultValue = "5") int size){
    PostListResponse postListResponse = postService.getPostList(page, size);
    model.addAttribute("posts", postListResponse.getPosts());
    model.addAttribute("totalPages", postListResponse.getTotalPages());
    model.addAttribute("currentPage", postListResponse.getCurrentPage());
    return "post/postList"; // Thymeleaf 파일 경로 수정
  }


  @GetMapping("/post/updateForm/{postId}")
  public String updateForm(@ModelAttribute("postId") Long postId, Model model){
    PostDto postDto = postService.findByPostId(postId);
    model.addAttribute("post", postDto);
    return "post/postUpdateForm";
  }
  @PostMapping("/post/updateForm/{postId}")
  public String update(@ModelAttribute("postId") Long postId, @ModelAttribute("post") PostCreateRequest request){
    postService.update(postId, PostDto.from(request));
    return "redirect:/post";
  }

  @GetMapping("/post/delete/{postId}")
  public String delete(@ModelAttribute("postId") Long postId){
    postService.delete(postId);
    return "redirect:/post";
  }
}
