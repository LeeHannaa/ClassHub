package com.example.classhub.domain.filedata.dto;

import com.example.classhub.domain.filedata.FileData;
import com.example.classhub.domain.filedata.controller.request.FileDataCreateRequest;
import com.example.classhub.domain.filedata.controller.request.FileDataUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FileDataDto {
    private Long id;
    private String fileDataName;
    private String fileDataType;
    private Long postId;

    public static FileDataDto from(FileDataCreateRequest request) {
        return FileDataDto.builder()
                .fileDataName(request.getFileDataName())
                .fileDataType(request.getFileDataType())
                .postId(request.getPostId())
                .build();
    }

    public static FileDataDto from(FileDataUpdateRequest request) {
        return FileDataDto.builder()
                .id(request.getId())
                .fileDataName(request.getFileDataName())
                .fileDataType(request.getFileDataType())
                .postId(request.getPostId())
                .build();
    }
    public static FileDataDto from(FileData fileData){
        return FileDataDto.builder()
                .id(fileData.getId())
                .fileDataName(fileData.getFileDataName())
                .fileDataType(fileData.getFileDataType())
                .postId(fileData.getPost().getPostId())
                .build();
    }

    public static FileDataDto from (Long postId){
        return FileDataDto.builder()
                .postId(postId)
                .build();
    }
}
