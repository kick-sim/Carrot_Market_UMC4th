package com.example.practicespring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;

@Getter
@AllArgsConstructor
public class PostAreaReq {
    private String address;
    private String zip_code;
}
