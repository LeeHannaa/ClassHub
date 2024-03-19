package com.example.classhub.domain.filedata.controller.response;

import com.example.classhub.domain.filedata.FileData;
import com.example.classhub.domain.filedata.dto.FileDataDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class FileDataResponse {
    private String fileDataName;
    private String fileDataType;

    public FileDataResponse(FileData fileData) {
        this.fileDataName = fileData.getFileDataName();
        this.fileDataType = fileData.getFileDataType();
    }

    public FileDataResponse(FileDataDto fileDataDto) {
        this.fileDataName = fileDataDto.getFileDataName();
        this.fileDataType = fileDataDto.getFileDataType();
    }
}
