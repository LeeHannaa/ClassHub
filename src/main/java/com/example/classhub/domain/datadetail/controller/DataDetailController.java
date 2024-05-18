package com.example.classhub.domain.datadetail.controller;

import com.example.classhub.domain.classhub_lroom.dto.LectureRoomDto;
import com.example.classhub.domain.classhub_lroom.service.LectureRoomService;
import com.example.classhub.domain.datadetail.controller.request.DataDetailCreateRequest;
import com.example.classhub.domain.datadetail.controller.request.DataDetailUpdateScoreRequest;
import com.example.classhub.domain.datadetail.controller.response.DataDetailListResponse;
import com.example.classhub.domain.datadetail.controller.response.DataStatisticListResponse;
import com.example.classhub.domain.datadetail.controller.response.DataStatisticResponse;
import com.example.classhub.domain.datadetail.dto.DataDetailDto;
import com.example.classhub.domain.datadetail.service.DataDetailService;
import com.example.classhub.domain.member.dto.MemberDto;
import com.example.classhub.domain.member.service.MemberService;
import com.example.classhub.domain.memberlroom.dto.MemberLRoomDto;
import com.example.classhub.domain.memberlroom.service.MemberLRoomService;
import com.example.classhub.domain.tag.ClassHub_Tag;
import com.example.classhub.domain.tag.controller.request.TagPerfectScoreUpdateRequest;
import com.example.classhub.domain.tag.controller.response.TagListResponse;
import com.example.classhub.domain.tag.controller.response.TagResponse;
import com.example.classhub.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@CrossOrigin("*")
public class DataDetailController {
    private final DataDetailService dataDetailService;
    private final LectureRoomService lectureRoomService;
    private final TagService tagService;
    private final MemberService memberService;
    private final MemberLRoomService memberLRoomService;

    @GetMapping("/data-detail/dataDetailForm")
    public String createDataDetailForm(Model model){
        model.addAttribute("dataDetail", new DataDetailCreateRequest());
        return "dataDetailForm";
    }
    @PostMapping("/data-detail/dataDetailForm")
    public String dataDetailForm(@ModelAttribute("dataDetailForm") DataDetailCreateRequest request){
        dataDetailService.saveDataDetail(DataDetailDto.from(request));
        return "redirect:/data-detail/dataDetailList";
    }

    @GetMapping("/data-detail/dataDetailList")
    public String findDataDetailList(Model model){
        DataDetailListResponse dataDetailListResponse = dataDetailService.getDataDetailList();
        model.addAttribute("dataDetails", dataDetailListResponse.getDataDetails());
        return "dataDetailList";
    }
    @GetMapping("/data-detail/dataDetailList/{tagId}/{studentNum}")
    public String findDataDetailListBySNum(Model model, @ModelAttribute("studentNum") String studentNum, @ModelAttribute("tagId") Long tagId){
        DataDetailListResponse dataDetailListResponse = dataDetailService.getDataDetailList(studentNum, tagId);
        model.addAttribute("dataDetails", dataDetailListResponse.getDataDetails());
        return "dataDetailList";
    }

    @GetMapping("/data-detail/updateForm/{dataDetailId}")
    public String updateForm(@ModelAttribute("dataDetailId") Long dataDetailId, Model model){
        DataDetailDto dataDetailDto = dataDetailService.findByDataDetailId(dataDetailId);
        model.addAttribute("dataDetail", dataDetailDto);
        return "dataDetailUpdateForm";
    }

    @PostMapping("/data-detail/updateForm/{dataDetailId}")
    public String update(@ModelAttribute("dataDetailId") Long dataDetailId, @ModelAttribute("dataDetail") DataDetailCreateRequest request){
        dataDetailService.update(dataDetailId, DataDetailDto.from(request));
        return "redirect:/data-detail/dataDetailList";
    }

    @GetMapping("/data-detail/delete/{dataDetailId}")
    public String delete(@ModelAttribute("dataDetailId") Long dataDetailId){
        dataDetailService.delete(dataDetailId);
        return "redirect:/data-detail/dataDetailList";
    }

    // statistics
    @GetMapping("/data-detail/statistics/{tagId}")
    public String statisticsByTagId(Model model, @ModelAttribute("tagId") Long tagId){
        DataStatisticListResponse dataStatisticListResponse = dataDetailService.getDataStatisticsList(tagId);
        model.addAttribute("dataStatistics", dataStatisticListResponse.getDataStatistic());

        Long lectureRoomId = tagService.findLRoomIdByTagId(tagId);
        LectureRoomDto lectureRoomDto = lectureRoomService.findByRoomId(lectureRoomId);
        model.addAttribute("lectureRoom", lectureRoomDto);

        TagListResponse tagListResponse = tagService.getTagListByLectureId(lectureRoomId);
        model.addAttribute("tags", tagListResponse.getTags());

        Integer perfectScore = tagService.getPerfectScoreByTagId(tagId);
        model.addAttribute("perfectScore", perfectScore);

      return "./statistical/statisticalData";
    }

    @GetMapping("/total/csv/download/{tagId}")
    public ResponseEntity<String> downloadCSV(@PathVariable("tagId") Long tagId){
        DataStatisticListResponse dataStatisticListResponse = dataDetailService.getDataStatisticsList(tagId);
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "text/csv; charset=UTF-8");
        header.add("Content-Disposition", "attachment; filename=\""+"student_data.csv"+"\"");
        return new ResponseEntity<String>(dataDetailService.setContent(dataStatisticListResponse), header, HttpStatus.CREATED);
    }

    @PutMapping("/data-detail/statistics/{tagId}/score")
    public ResponseEntity<String> updateScoreByTagId(@PathVariable Long tagId, @RequestBody DataDetailUpdateScoreRequest request) {
      dataDetailService.updateScore(tagId, DataDetailDto.from(request));
      return ResponseEntity.ok("redirect:/data-detail/statistics/" + tagId);
    }


    @PutMapping("/data-detail/statistics/{tagId}/perfect-score")
    public ResponseEntity<?> updateTagPerfectScore(@PathVariable Long tagId, @RequestBody TagPerfectScoreUpdateRequest request) {
      tagService.updatePerfectScore(tagId, request);
      return ResponseEntity.ok().build();
    }

    @GetMapping("student/{uniqueId}/{LRoomId}")
    public String findStudentList(@PathVariable Long LRoomId,@PathVariable String uniqueId, Model model){
      // uniqueId로 member_id 찾기
      MemberDto member = memberService.findByUniqueIdDto(uniqueId);
      LectureRoomDto lectureRoomDto = lectureRoomService.findByRoomId(LRoomId);
      model.addAttribute("lectureRoom", lectureRoomDto);
      if (member != null) {
        // member_id와 LroomId가 모두 일치하는지 확인
        MemberLRoomDto memberLroom = memberLRoomService.findByMemberIdAndLroomId(member.getMemberId(), LRoomId);
        if (memberLroom != null) {
          // Lroom에 연결된 태그 목록 찾기
          TagListResponse tagListResponse = tagService.getTagListByLectureId(LRoomId);
          model.addAttribute("tags", tagListResponse);

          // datadetail에서 해당 태그와 일치하는 데이터 목록을 가져오기
          List<DataStatisticResponse> matchedDataDetails = new ArrayList<>();
          for (TagResponse tag : tagListResponse.getTags()) {
            List<DataStatisticResponse> dataDetails = dataDetailService.findByTagTagId(tag.getTagId());
            matchedDataDetails.addAll(dataDetails);
          }
          // 가져온 datadetail 데이터 목록을 모델에 추가
          model.addAttribute("dataDetails", matchedDataDetails);

          // 여기서 studentNum과 일치하는 DataDetailDto 찾기
          List<DataStatisticResponse> matchedStudentDetails = matchedDataDetails.stream()
            .filter(dataDetail -> uniqueId.equals(dataDetail.getStudentNum()))
            .collect(Collectors.toList());

          // 일치하는 학생 정보를 모델에 추가
          model.addAttribute("matchedStudentDetails", matchedStudentDetails);

          // matchedDataDetails 리스트를 태그 ID 별로 그룹화
          Map<Long, List<DataStatisticResponse>> groupedByTagId = matchedDataDetails.stream()
            .collect(Collectors.groupingBy(DataStatisticResponse::getTagId));

          Map<Long, Map<String, Double>> statisticsByTagId = new HashMap<>();
          DecimalFormat df = new DecimalFormat("#.#");

          groupedByTagId.forEach((tagId, details) -> {
            DoubleSummaryStatistics stats = details.stream()
              .mapToDouble(DataStatisticResponse::getScore)
              .summaryStatistics();

            double average = Double.parseDouble(df.format(stats.getAverage()));
            double max = Double.parseDouble(df.format(stats.getMax()));
            double min = Double.parseDouble(df.format(stats.getMin()));

            // 표준편차 계산
            double finalAverage = stats.getAverage();
            double stddev = Math.sqrt(details.stream()
              .mapToDouble(d -> Math.pow(d.getScore() - finalAverage, 2))
              .average()
              .orElse(0));
            stddev = Double.parseDouble(df.format(stddev));

            // 0점을 제외한 평균값 계산
            double averageExcludingZero = details.stream()
              .filter(d -> d.getScore() > 0)
              .mapToDouble(DataStatisticResponse::getScore)
              .average()
              .orElse(0);
            averageExcludingZero = Double.parseDouble(df.format(averageExcludingZero));

            // 결과 맵에 추가
            Map<String, Double> statsMap = new HashMap<>();
            statsMap.put("average", average);
            statsMap.put("max", max);
            statsMap.put("min", min);
            statsMap.put("stddev", stddev);
            statsMap.put("averageExcludingZero", averageExcludingZero);

            statisticsByTagId.put(tagId, statsMap);
          });

          // 계산된 통계 정보를 모델에 추가
          model.addAttribute("statisticsByTagId", statisticsByTagId);



          return "./student/studentView";
        }
      }
      return "errorPage";
    }
}
