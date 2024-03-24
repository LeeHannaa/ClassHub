package com.example.classhub.domain.datadetail.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class DataDetailListResponse {
    private List<DataDetailResponse> dataDetails;

    public DataDetailListResponse(List<DataDetailResponse> dataDetails){
        this.dataDetails = dataDetails;
    }
}