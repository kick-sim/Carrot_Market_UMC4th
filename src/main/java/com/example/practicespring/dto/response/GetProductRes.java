package com.example.practicespring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProductRes {
    private String seller;
    private String category;
    private String zipCode;
    private String title;
    private int price;
    private String content;
}
