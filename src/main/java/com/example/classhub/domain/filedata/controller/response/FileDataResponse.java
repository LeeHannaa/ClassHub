package com.example.classhub.domain.filedata.controller.response;

import com.example.classhub.domain.filedata.FileData;
import com.example.classhub.domain.filedata.dto.FileDataDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class FileDataResponse {
    private Long id;
    private String fileDataName;
    private String fileDataType;

    public FileDataResponse(FileData fileData) {
        this.id = fileData.getId();
        this.fileDataName = fileData.getFileDataName();
        this.fileDataType = fileData.getFileDataType();
    }

    public FileDataResponse(FileDataDto fileDataDto) {
        this.id = fileDataDto.getId();
        this.fileDataName = fileDataDto.getFileDataName();
        this.fileDataType = fileDataDto.getFileDataType();
    }
}
