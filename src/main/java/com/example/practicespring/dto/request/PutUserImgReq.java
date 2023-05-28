package com.example.practicespring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PutUserImgReq {
    private long userId;
    private String img_url;
}
