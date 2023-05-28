package com.example.practicespring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class PostAreaRes {
    private String address;
    private String zip_code;
}
