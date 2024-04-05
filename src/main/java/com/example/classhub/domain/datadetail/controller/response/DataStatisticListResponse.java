package com.example.classhub.domain.datadetail.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class DataStatisticListResponse {
    private List<DataStatisticResponse> dataStatistic;

    public DataStatisticListResponse(List<DataStatisticResponse> dataStatistic){
        this.dataStatistic = dataStatistic;
    }
}
