
package com.example.classhub.domain.datadetail.controller.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class DataDetailCreateRequest {
    private String studentNum;
    private Double score;
    private String comment;
    private Long tagId;
}
