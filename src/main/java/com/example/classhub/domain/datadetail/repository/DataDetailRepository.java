package com.example.classhub.domain.datadetail.repository;

import com.example.classhub.domain.datadetail.ClassHub_DataDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataDetailRepository extends JpaRepository<ClassHub_DataDetail, Long> {
    List<ClassHub_DataDetail> findByStudentNumAndTagTagId(String studentNum, Long tagId);
}
