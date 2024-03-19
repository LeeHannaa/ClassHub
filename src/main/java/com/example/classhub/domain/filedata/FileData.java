package com.example.classhub.domain.filedata;

import com.example.classhub.domain.BaseEntity;
import com.example.classhub.domain.filedata.dto.FileDataDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileData extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileDataName;
    private String fileDataType;

    public static FileData from(FileDataDto fileDataDto) {
        return FileData.builder()
                .fileDataName(fileDataDto.getFileDataName())
                .fileDataType(fileDataDto.getFileDataType())
                .build();
    }
}
