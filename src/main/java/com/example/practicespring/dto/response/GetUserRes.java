package com.example.practicespring.dto.response;

import lombok.Getter;

@Getter
public class GetUserRes {
    private String email;
    private String password;
    private String nickname;
    private float manner_rate;
    private String img_url;

    public GetUserRes(String email, String password, String nickname, float manner_rate) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.manner_rate = manner_rate;
        this.img_url = " ";
    }

    public GetUserRes(String email, String password, String nickname, float manner_rate, String img_url) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.manner_rate = manner_rate;
        this.img_url = img_url;
    }

}
