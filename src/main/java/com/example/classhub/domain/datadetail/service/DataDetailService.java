package com.example.classhub.domain.datadetail.service;

import com.example.classhub.domain.classhub_lroom.ClassHub_LRoom;
import com.example.classhub.domain.classhub_lroom.dto.LectureRoomDto;
import com.example.classhub.domain.classhub_lroom.service.LectureRoomService;
import com.example.classhub.domain.datadetail.ClassHub_DataDetail;
import com.example.classhub.domain.datadetail.controller.response.DataDetailListResponse;
import com.example.classhub.domain.datadetail.controller.response.DataDetailResponse;
import com.example.classhub.domain.datadetail.controller.response.DataStatisticListResponse;
import com.example.classhub.domain.datadetail.controller.response.DataStatisticResponse;
import com.example.classhub.domain.datadetail.dto.DataDetailDto;
import com.example.classhub.domain.datadetail.repository.DataDetailRepository;
import com.example.classhub.domain.member.ClassHub_Member;
import com.example.classhub.domain.member.service.MemberService;
import com.example.classhub.domain.memberlroom.ClassHub_MemberLRoom;
import com.example.classhub.domain.memberlroom.service.MemberLRoomService;
import com.example.classhub.domain.tag.ClassHub_Tag;
import com.example.classhub.domain.tag.controller.response.TagListResponse;
import com.example.classhub.domain.tag.controller.response.TagResponse;
import com.example.classhub.domain.tag.service.TagService;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataDetailService {
    private final DataDetailRepository dataDetailRepository;
    private final LectureRoomService lectureRoomService;
    private final TagService tagService;
    private final MemberService memberService;
    private final MemberLRoomService memberLRoomService;
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

    public DataDetailListResponse getDataDetailList(String studentNum, Long tagId) {
        List<ClassHub_DataDetail> dataDetails = dataDetailRepository.findByStudentNumAndTagTagId(studentNum, tagId);
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

    @Transactional
    public DataDetailDto updateScore(Long dataDetailId, DataDetailDto dataDetailDto) {
        ClassHub_DataDetail dataDetail = dataDetailRepository.findById(dataDetailId)
                .orElseThrow(() -> new IllegalArgumentException("해당 데이터 상세가 존재하지 않습니다."));
        dataDetail.updateScore(dataDetailDto);
        return DataDetailDto.from(dataDetail);
    }

    //statistics
    @Transactional
    public DataStatisticListResponse getDataStatisticsList(Long tagId) {
        Long roomId = tagService.findLRoomIdByTagId(tagId);
        List<ClassHub_MemberLRoom> memberList = memberLRoomService.findMembersByLRoomId(roomId);
        System.out.println("memberList"+ memberList);
        if(memberList.isEmpty()) return new DataStatisticListResponse(Collections.emptyList());

        List<ClassHub_DataDetail> dataStatistics = dataDetailRepository.findByTagTagId(tagId);
        System.out.println("dataStatistics"+ dataStatistics);

        List<ClassHub_DataDetail> lastList = findMatchingDataDetails(memberList, dataStatistics);

        List<DataStatisticResponse> dataStatisticResponses = lastList.stream()
                .map(DataStatisticResponse::new)
                .toList();
        return new DataStatisticListResponse(dataStatisticResponses);
    }

    private List<ClassHub_DataDetail> findMatchingDataDetails(List<ClassHub_MemberLRoom> memberList, List<ClassHub_DataDetail> dataStatistics) {
        List<String> uniqueIds = memberList.stream()
                .map(memberLRoom -> memberLRoom.getClassHubMember().getUniqueId())
                .collect(Collectors.toList());
        return dataStatistics.stream()
                .filter(dataDetail -> uniqueIds.contains(dataDetail.getStudentNum()))
                .collect(Collectors.toList());
    }

    public String setContent(DataStatisticListResponse dataStatisticListResponses){
        String data = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        data += "학번, 이름, 점수, 생성일, 수정일 \n";
        for(int i=0; i<dataStatisticListResponses.getDataStatistic().size(); i++){
            data += dataStatisticListResponses.getDataStatistic().get(i).getStudentNum() + ",";
            data += dataStatisticListResponses.getDataStatistic().get(i).getStudentName() + ",";
            data += dataStatisticListResponses.getDataStatistic().get(i).getScore() + ",";
            data += dataStatisticListResponses.getDataStatistic().get(i).getRegDate() + ",";
            data += dataStatisticListResponses.getDataStatistic().get(i).getModDate() + "\n";
        }
        return data;
    }

  public List<DataStatisticResponse> findByTagTagId(Long tagId) {
    List<ClassHub_DataDetail> dataDetails = dataDetailRepository.findByTagTagId(tagId);
    List<DataStatisticResponse> dataStatisticResponses = dataDetails.stream()
            .map(DataStatisticResponse::new)
            .toList();
    return dataStatisticResponses;
  }
}
