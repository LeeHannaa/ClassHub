package com.example.classhub.domain.filedata.service;

import com.example.classhub.domain.filedata.FileData;
import com.example.classhub.domain.filedata.controller.response.FileDataListResponse;
import com.example.classhub.domain.filedata.controller.response.FileDataResponse;
import com.example.classhub.domain.filedata.dto.FileDataDto;
import com.example.classhub.domain.filedata.repository.FileDataRepository;
import com.example.classhub.domain.post.ClassHub_Post;
import com.example.classhub.domain.post.repository.PostRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileDataService {
    private final FileDataRepository fileDataRepository;
    private final PostRepository postRepository;
    @Transactional
    public FileDataDto saveFileData(FileDataDto fileDataDto) {
        ClassHub_Post post = postRepository.findById(fileDataDto.getPostId()).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        FileData filedata = FileData.from(fileDataDto, post);
        fileDataRepository.save(filedata);

        return FileDataDto.from(filedata);
    }

    public FileDataListResponse getFileDataList() {
        List<FileData> fileDatas = fileDataRepository.findAll();
        List<FileDataResponse> fileDataResponses = fileDatas.stream()
                .map(FileDataResponse::new)
                .collect(Collectors.toList());
        return new FileDataListResponse(fileDataResponses);
    }

    @Transactional
    public FileDataDto findByFileDataId(Long fileDataId) {
        FileData fileData = fileDataRepository.findById(fileDataId).orElseThrow(() -> new IllegalArgumentException("해당 파일이 존재하지 않습니다."));
        return FileDataDto.from(fileData);
    }
    @Transactional
    public FileDataDto update(Long fileDataId, FileDataDto fileDataDto) {
        FileData fileData = fileDataRepository.findById(fileDataId).orElseThrow(() -> new IllegalArgumentException("해당 파일이 존재하지 않습니다."));

        fileData.update(fileDataDto);
        fileDataRepository.save(fileData);
        return FileDataDto.from(fileData);
    }

    @Transactional
    public void delete(Long fileDataId) {
        fileDataRepository.deleteById(fileDataId);
    }
}
