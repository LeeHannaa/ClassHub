package com.example.classhub.domain.post.controller;

import com.example.classhub.domain.classhub_lroom.ClassHub_LRoom;
import com.example.classhub.domain.classhub_lroom.service.LectureRoomService;
import com.example.classhub.domain.post.controller.request.PostCheckRequest;
import com.example.classhub.domain.post.controller.request.PostCreateRequest;
import com.example.classhub.domain.post.controller.request.PostUpdateRequest;
import com.example.classhub.domain.post.dto.PostDto;
import com.example.classhub.domain.post.service.PostService;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin("*")
public class PostController {
    private final PostService postService;
    private final LectureRoomService lectureRoomService;

    @GetMapping("/post/postForm")
    public String createPostForm(Model model) {
        model.addAttribute("post", new PostCreateRequest());
        return "post/postForm";
    }

    @PostMapping("/post/postForm")
    public ResponseEntity<?> postForm(@ModelAttribute("postForm") PostCreateRequest request,
                                      @RequestParam(value = "file", required = false) MultipartFile file,
                                      HttpSession session) throws IOException {

        Long lRoomId = request.getLRoomId();
        ClassHub_LRoom lRoom = lectureRoomService.findByRoomId(lRoomId);
        session.setAttribute("postForm", request);
        session.setAttribute("lRoom", lRoom);
        if (file != null && !file.isEmpty()) {
            String tempDir = System.getProperty("java.io.tmpdir");
            File tempFile = new File(tempDir, file.getOriginalFilename());
            file.transferTo(tempFile);

            session.setAttribute("filePath", tempFile.getAbsolutePath());
            List<String> headers = postService.checkHeader(tempFile);
            session.setAttribute("headers", headers);
            return ResponseEntity.ok().build();
        } else {
            PostDto postDto = PostDto.from(request, null);
            postService.createPost(postDto, null);
            return ResponseEntity.noContent().build();
        }
    }


    @GetMapping("/post/postModal")
    public String postModal(HttpSession session, Model model) {
        List<String> headers = (List<String>) session.getAttribute("headers");
        ClassHub_LRoom lRoom = (ClassHub_LRoom) session.getAttribute("lRoom");
        boolean keyHeaderExists = headers.stream().anyMatch(header -> header.contains(lRoom.getStudentInfoKey()));
        model.addAttribute("lRoomId", lRoom.getLRoomId());
        model.addAttribute("headers", headers);
        model.addAttribute("keyHeaderExists", keyHeaderExists);
        model.addAttribute("keyHeaderName", lRoom.getStudentInfoKey());
        return "post/postModal";
    }


    @PostMapping("/post/postModal")
    public String savePost(@ModelAttribute PostCheckRequest postCheckRequest,
                           HttpSession session) throws IOException {
        String filePath = (String) session.getAttribute("filePath");
        File file = new File(filePath);

        PostCreateRequest postCreateRequest = (PostCreateRequest) session.getAttribute("postForm");
        postService.createPost(PostDto.from(postCreateRequest, postCheckRequest), file);

        Long lRoomId = postCreateRequest.getLRoomId();

        file.delete();
        return "redirect:/lecture-room/detail/" + lRoomId;
    }

    @GetMapping("/post/{lRoomId}")
    public String findPostListByLectureRoomId(@PathVariable Long lRoomId, Model model) {
        List<PostDto> postList = postService.getPostListByLectureRoomId(lRoomId);
        model.addAttribute("posts", postList);
        return "post/postList"; // Thymeleaf 파일 경로 수정
    }

    @GetMapping("/post/updateForm/{postId}")
    public String updateForm(@ModelAttribute("postId") Long postId, Model model) {
        PostDto postDto = postService.findByPostId(postId);
        model.addAttribute("post", postDto);
        return "post/postUpdateForm";
    }

    @PostMapping("/post/updateForm/{postId}")
    public ResponseEntity<String> update(@PathVariable Long postId, @RequestBody PostUpdateRequest request) {
        System.out.println("PostUpdateRequest" + request);
        postService.update(postId, PostDto.from(request));
        return ResponseEntity.ok(postId+"수정 성공");
    }

    @PostMapping("/post/delete/{postId}")
    public ResponseEntity<String> delete(@ModelAttribute("postId") Long postId) {
        postService.delete(postId);
        return ResponseEntity.ok(postId+"삭제");
    }
}
