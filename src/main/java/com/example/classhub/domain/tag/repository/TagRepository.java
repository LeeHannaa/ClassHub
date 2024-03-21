package com.example.classhub.domain.tag.repository;

import com.example.classhub.domain.classhub_lroom.ClassHub_LRoom;
import com.example.classhub.domain.tag.ClassHub_Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<ClassHub_Tag, Long> {
    List<ClassHub_Tag> findByLectureRoom(ClassHub_LRoom lectureRoom);
}
