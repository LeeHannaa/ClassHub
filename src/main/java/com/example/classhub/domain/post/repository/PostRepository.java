package com.example.classhub.domain.post.repository;

import com.example.classhub.domain.post.ClassHub_Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<ClassHub_Post, Long> {

    List<ClassHub_Post> findBylRoom_lRoomId(Long lRoomId);
}
