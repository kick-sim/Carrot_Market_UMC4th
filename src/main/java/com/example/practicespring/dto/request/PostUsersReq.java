package com.example.practicespring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostUsersReq {
    private String phone_number;
    private String nickname;
}
