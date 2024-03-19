package com.example.classhub.domain.filedata.controller.response;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class FileDataListResponse {
    private List<FileDataResponse> fileDatas;

    public FileDataListResponse(List<FileDataResponse> fileDatas){
        this.fileDatas = fileDatas;
    }
}
