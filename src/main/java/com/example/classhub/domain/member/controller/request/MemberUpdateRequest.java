package com.example.classhub.domain.member.controller.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class MemberUpdateRequest {
    private String email;
    private String department;
}
