package com.example.practicespring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostUsersRes {
    private String Email;
    private String nickname;
    private float manner_rate;
}
