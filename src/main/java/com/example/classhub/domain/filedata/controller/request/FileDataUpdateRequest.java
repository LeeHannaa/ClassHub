package com.example.classhub.domain.filedata.controller.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class FileDataUpdateRequest {
    private Long id;
    private String fileDataName;
    private String fileDataType;
    private Long postId;
}
