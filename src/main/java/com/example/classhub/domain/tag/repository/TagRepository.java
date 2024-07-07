package com.example.classhub.domain.tag.repository;

import com.example.classhub.domain.classhub_lroom.ClassHub_LRoom;
import com.example.classhub.domain.tag.ClassHub_Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<ClassHub_Tag, Long> {
    List<ClassHub_Tag> findByLectureRoom(ClassHub_LRoom lectureRoom);

    @Query("SELECT t FROM ClassHub_Tag t WHERE t.name = :name AND t.lectureRoom.lRoomId = :lRoomId")
    ClassHub_Tag findByNameAndLectureRoom_LRoomId(@Param("name") String name, @Param("lRoomId") Long lRoomId);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM ClassHub_Tag t WHERE t.name = :name AND t.lectureRoom.lRoomId = :lRoomId")
    boolean existsByNameAndLectureRoom_LRoomId(@Param("name") String name, @Param("lRoomId") Long lRoomId);

    List<ClassHub_Tag> findByTagIdIn(List<Long> tagIdList);

  @Query("SELECT t FROM ClassHub_Tag t JOIN t.dataDetails dd WHERE t.lectureRoom.lRoomId = :lRoomId AND dd.studentNum = :studentNum")
  List<ClassHub_Tag> findByLectureRoomIdAndStudentNum(@Param("lRoomId") Long lRoomId, @Param("studentNum") String studentNum);
}
