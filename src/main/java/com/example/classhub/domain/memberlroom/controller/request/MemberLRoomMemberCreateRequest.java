package com.example.classhub.domain.memberlroom.controller.request;

import com.example.classhub.domain.memberlroom.dto.Permission;
import com.example.classhub.domain.memberlroom.dto.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberLRoomMemberCreateRequest {
    private String name;
    private String uniqueId;
    private String email;
    private Role role;
    private Permission permission;
}
