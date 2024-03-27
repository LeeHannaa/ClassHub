package com.example.classhub.domain.datadetail.service;

import com.example.classhub.domain.datadetail.ClassHub_DataDetail;
import com.example.classhub.domain.datadetail.controller.response.DataDetailListResponse;
import com.example.classhub.domain.datadetail.controller.response.DataDetailResponse;
import com.example.classhub.domain.datadetail.dto.DataDetailDto;
import com.example.classhub.domain.datadetail.repository.DataDetailRepository;
import com.example.classhub.domain.tag.ClassHub_Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataDetailService {
    private final DataDetailRepository dataDetailRepository;
    @Transactional
    public void saveDataDetail(DataDetailDto dataDetailDto) {
        ClassHub_Tag tag = ClassHub_Tag.builder()
                .tagId(dataDetailDto.getTagId())
                .build();
        ClassHub_DataDetail dataDetail = ClassHub_DataDetail.from(dataDetailDto, tag);
        dataDetailRepository.save(dataDetail);
    }

    public DataDetailListResponse getDataDetailList() {
        List<ClassHub_DataDetail> dataDetails = dataDetailRepository.findAll();
        List<DataDetailResponse> dataDetailResponses = dataDetails.stream()
                .map(DataDetailResponse::new)
                .toList();
        return new DataDetailListResponse(dataDetailResponses);
    }

    @Transactional
    public DataDetailDto findByDataDetailId(Long dataDetailId) {
        ClassHub_DataDetail dataDetail = dataDetailRepository.findById(dataDetailId)
                .orElseThrow(() -> new IllegalArgumentException("해당 데이터 상세가 존재하지 않습니다."));
        return DataDetailDto.from(dataDetail);
    }

    @Transactional
    public void delete(Long dataDetailId) {
        dataDetailRepository.deleteById(dataDetailId);
    }

    @Transactional
    public DataDetailDto update(Long dataDetailId, DataDetailDto dataDetailDto) {
        ClassHub_DataDetail dataDetail = dataDetailRepository.findById(dataDetailId)
                .orElseThrow(() -> new IllegalArgumentException("해당 데이터 상세가 존재하지 않습니다."));
        dataDetail.update(dataDetailDto);
        return DataDetailDto.from(dataDetail);
    }
}
