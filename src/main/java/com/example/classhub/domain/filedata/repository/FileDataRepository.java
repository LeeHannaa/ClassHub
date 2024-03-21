package com.example.classhub.domain.filedata.repository;

import com.example.classhub.domain.filedata.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDataRepository extends JpaRepository<FileData, Long> {
}
