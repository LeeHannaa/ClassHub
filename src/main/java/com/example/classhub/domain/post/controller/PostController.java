package com.example.classhub.domain.post.controller;

import com.example.classhub.domain.datadetail.service.DataDetailService;
import com.example.classhub.domain.post.controller.request.PostCheckRequest;
import com.example.classhub.domain.post.controller.request.PostCreateRequest;
import com.example.classhub.domain.post.controller.response.PostListResponse;
import com.example.classhub.domain.post.dto.PostDto;
import com.example.classhub.domain.post.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public String postForm(@ModelAttribute("postForm") PostCreateRequest request, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, HttpSession session) throws IOException {
        // 임시 파일 생성 및 세션에 파일 경로 저장
        String tempDir = System.getProperty("java.io.tmpdir");
        File tempFile = new File(tempDir, file.getOriginalFilename());
        file.transferTo(tempFile);

        session.setAttribute("filePath", tempFile.getAbsolutePath());
        session.setAttribute("postForm", request);

        List<String> headers = postService.checkHeader(tempFile); // 수정된 부분: MultipartFile 대신 File 사용
        redirectAttributes.addFlashAttribute("headers", headers);
        return "redirect:/post/postHeaderCheckForm";
    }

    @GetMapping("/post/postHeaderCheckForm")
    public String postHeaderCheckForm(@ModelAttribute("headers") List<String> headers, HttpSession session, Model model){
        MultipartFile file = (MultipartFile) session.getAttribute("file");

        model.addAttribute("headers", headers);
        model.addAttribute("file", file);
        return "postHeaderCheckForm";
    }

    @PostMapping("/post/postHeaderCheckForm")
    public String savePost(@RequestParam(required = false) List<Boolean> isSelected,
                           @RequestParam(required = false) List<Boolean> isScore,
                           HttpSession session) throws IOException {

        String filePath = (String) session.getAttribute("filePath");
        File file = new File(filePath);

        PostCreateRequest postCreateRequest = (PostCreateRequest) session.getAttribute("postForm");
        PostCheckRequest postCheckRequest = new PostCheckRequest();

        postCheckRequest.setIsSelected(isSelected);
        postCheckRequest.setIsScore(isScore);

        if (isSelected != null && isSelected.contains(true)) {
            postCheckRequest.setTagNames(postService.checkHeader(file));
        }
        System.out.println("postCheckRequest.getTagNames()" + postCheckRequest.getTagNames());


        postService.createPost(PostDto.from(postCreateRequest, postCheckRequest), file);
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
