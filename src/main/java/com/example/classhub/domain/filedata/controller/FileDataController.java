package com.example.classhub.domain.filedata.controller;

import com.example.classhub.domain.filedata.controller.request.FileDataCreateRequest;
import com.example.classhub.domain.filedata.controller.response.FileDataListResponse;
import com.example.classhub.domain.filedata.dto.FileDataDto;
import com.example.classhub.domain.filedata.service.FileDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@CrossOrigin("*")
public class FileDataController {
    private final FileDataService fileDataService;

    @GetMapping("/file-data/fileDataForm")
    public String createFileDataForm(Model model) {
        model.addAttribute("fileData", new FileDataCreateRequest());
        return "fileDataForm";
    }
    @PostMapping("/file-data/fileDataForm")
    public String saveFileData(@ModelAttribute("fileData") FileDataCreateRequest request) {
        fileDataService.saveFileData(FileDataDto.from(request));
        return "redirect:/file-data/fileDataList";
    }

    @GetMapping("/file-data/fileDataList")
    public String findFileDataList(Model model){
        FileDataListResponse fileDataListResponse = fileDataService.getFileDataList();
        model.addAttribute("fileDatas", fileDataListResponse.getFileDatas());
        return "fileDataList";
    }



}
