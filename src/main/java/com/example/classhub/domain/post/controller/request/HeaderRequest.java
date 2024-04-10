package com.example.classhub.domain.post.controller.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class HeaderRequest {
    private List<String> headers;
}
