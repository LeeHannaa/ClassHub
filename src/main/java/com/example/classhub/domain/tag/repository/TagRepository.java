package com.example.classhub.domain.tag.repository;

import com.example.classhub.domain.lectureroom.LectureRoom;
import com.example.classhub.domain.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByLectureRoom(LectureRoom lectureRoom);
}
