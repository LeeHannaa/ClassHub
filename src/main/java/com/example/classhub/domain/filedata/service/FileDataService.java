package com.example.classhub.domain.filedata.service;

import com.example.classhub.domain.filedata.FileData;
import com.example.classhub.domain.filedata.controller.response.FileDataListResponse;
import com.example.classhub.domain.filedata.controller.response.FileDataResponse;
import com.example.classhub.domain.filedata.dto.FileDataDto;
import com.example.classhub.domain.filedata.repository.FileDataRepository;
import com.example.classhub.domain.member.Member;
import com.example.classhub.domain.member.controller.response.MemberListResponse;
import com.example.classhub.domain.member.controller.response.MemberResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileDataService {
    private final FileDataRepository fileDataRepository;
    @Transactional
    public FileDataDto saveFileData(FileDataDto fileDataDto) {
        FileData filedata = FileData.from(fileDataDto);
        fileDataRepository.save(filedata);

        return FileDataDto.from(filedata);
    }

    public FileDataListResponse getFileDataList() {
        List<FileData> fileDatas = fileDataRepository.findAll();
        List<FileDataResponse> fileDataResponses = fileDatas.stream()
                .map(FileDataResponse::new)
                .toList();
        return new FileDataListResponse(fileDataResponses);
    }


}
