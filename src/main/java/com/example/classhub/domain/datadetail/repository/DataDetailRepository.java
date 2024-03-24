package com.example.classhub.domain.datadetail.repository;

import com.example.classhub.domain.datadetail.ClassHub_DataDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataDetailRepository extends JpaRepository<ClassHub_DataDetail, Long> {
}
