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
import com.example.classhub.domain.member.service.MemberService;
import com.example.classhub.domain.tag.controller.request.TagPerfectScoreUpdateRequest;
import com.example.classhub.domain.tag.controller.response.TagListResponse;
import com.example.classhub.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@CrossOrigin("*")
public class DataDetailController {
    private final DataDetailService dataDetailService;
    private final LectureRoomService lectureRoomService;
    private final TagService tagService;
    private final MemberService memberService;

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
}
