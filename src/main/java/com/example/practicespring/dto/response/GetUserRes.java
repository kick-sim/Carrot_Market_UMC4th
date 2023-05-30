package com.example.practicespring.dto.response;

import com.example.practicespring.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import java.util.List;
import java.util.stream.Collector;

@Getter
public class GetUserRes {
    private String phone_number;
    private String nickname;
    private float manner_rate;
    private String img_url;

    public GetUserRes(String phone_number, String nickname, float manner_rate) {
        this.phone_number = phone_number;
        this.nickname = nickname;
        this.manner_rate = manner_rate;
        this.img_url = " ";
    }

    public GetUserRes(String phone_number, String nickname, float manner_rate, String img_url) {
        this.phone_number = phone_number;
        this.nickname = nickname;
        this.manner_rate = manner_rate;
        this.img_url = img_url;
    }

}
