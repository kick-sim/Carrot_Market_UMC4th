package com.example.practicespring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostLoginReq {
    private String email;
    private String password;
}
