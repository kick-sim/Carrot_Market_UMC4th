package com.example.practicespring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostUsersReq {
    private String email;
    private String password;
    private String nickname;
}
